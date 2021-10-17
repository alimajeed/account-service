package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.mapper.UserMapper;
import com.kyc.accountservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    UserMapper userMapper;
    UserRepository userRepository;

    @Override
    public User saveUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    @Override
    public UserDto mapToUserDto(User createdUser) {
        UserDto returnUserDto = userMapper.userToUserDto(createdUser);
        return returnUserDto;
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }
}
