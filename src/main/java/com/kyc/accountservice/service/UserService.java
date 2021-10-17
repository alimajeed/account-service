package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.UserDto;

import java.util.Optional;

public interface UserService {

    User saveUser(UserDto userDto);
    UserDto mapToUserDto(User createdUser);
    Optional<User> findByUserId(Long userId);
}
