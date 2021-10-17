package com.kyc.accountservice.service;

import com.kyc.accountservice.config.PropertyConfig;
import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AccountDto;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.EntityNotFoundException;
import com.kyc.accountservice.exception.FileStorageException;
import com.kyc.accountservice.exception.UserVerificationFailedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
@Slf4j
public class BankServiceImpl implements BankService {
    FileStorageService fileStorageService;
    ThirdPartyVerificationService verificationService;
    AccountService accountService;
    UserService userService;

    @Override
    @Transactional
    public Long createUserAndGenerateAccount(UserDto userDto, MultipartFile file, HttpServletRequest request) throws UserVerificationFailedException, FileStorageException {
        CompletableFuture<Boolean> isVerifiedFuture = verificationService.verifyUser(userDto);
        CompletableFuture<String> savedPathFuture = fileStorageService.storeFile(file);

        String docUrl = ServletUriComponentsBuilder.fromContextPath(request)
                .path(PropertyConfig.FILE_DOWNLOAD_CONTROLLER_PATH)
                .path(file.getOriginalFilename())
                .toUriString();

        userDto.setDocUrl(docUrl);
        userDto.setCreatedAt(LocalDateTime.now());
        User createdUser = userService.saveUser(userDto);
        Account createdAccount = accountService.createAccount(createdUser);

        AccountDto createdAccountDto = accountService.mapToAccountDto(createdAccount);
        UserDto createdUserDto = userService.mapToUserDto(createdUser);
        createdUserDto.setAccount(createdAccountDto);
        boolean isVerified = verificationService.getFutureResults(isVerifiedFuture);
        log.info("isVerified : {} for User : {}", isVerified, userDto.getName());
        if (!isVerified){
            throw new UserVerificationFailedException("User verification failed for : " + userDto.getName());
        }
        CompletableFuture.allOf(savedPathFuture).join();
        return createdUserDto.getUserId();
    }

    @Override
    @Transactional
    public boolean updateUserDetails(UserDto userDto) {
        Optional<User> foundUserOptional = userService.findByUserId(userDto.getUserId());
        UserDto foundUserDto = userService.mapToUserDto(foundUserOptional.orElseThrow(() -> new EntityNotFoundException(User.class, "userId", String.valueOf(userDto.getUserId()))));
        BeanUtils.copyProperties(userDto, foundUserDto, getNullPropertyNames(userDto));
        CompletableFuture<Boolean> isVerifiedFuture = verificationService.verifyUser(foundUserDto);
        foundUserDto.setLastUpdatedAt(LocalDateTime.now());
        User updatedUser = userService.saveUser(foundUserDto);
        boolean isVerified = verificationService.getFutureResults(isVerifiedFuture);
        log.info("isVerified : {}", isVerified);
        if (!isVerified){
            throw new UserVerificationFailedException("User verification failed for userId : " + userDto.getUserId());
        }
        return true;
    }

    @Override
    public UserDto fetchUserDetails(Long userId) {
        Optional<User> foundUserOptional = userService.findByUserId(userId);
        UserDto foundUserDto = userService.mapToUserDto(foundUserOptional.orElseThrow(() -> new EntityNotFoundException(User.class, "userId", String.valueOf(userId))));
        Optional<Account> foundAccountOptional = accountService.findByUser(foundUserOptional.get());
        AccountDto foundAccountDto = accountService.mapToAccountDto(foundAccountOptional.orElseThrow(() -> new EntityNotFoundException(Account.class, "userId", String.valueOf(userId))));
        foundUserDto.setAccount(foundAccountDto);
        return foundUserDto;
    }

    private static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
