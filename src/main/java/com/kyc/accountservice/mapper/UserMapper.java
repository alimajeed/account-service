package com.kyc.accountservice.mapper;

import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.UserDto;

public interface UserMapper {
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
}
