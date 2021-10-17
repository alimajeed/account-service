package com.kyc.accountservice.service;

import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.FileStorageException;
import com.kyc.accountservice.exception.UserVerificationFailedException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface BankService {
    Long createUserAndGenerateAccount(UserDto userDto, MultipartFile file, HttpServletRequest request) throws UserVerificationFailedException, FileStorageException;
    boolean updateUserDetails(UserDto userDto);
    UserDto fetchUserDetails(Long userId);
}
