package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.Address;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.mapper.UserMapper;
import com.kyc.accountservice.mapper.UserMapperImpl;
import com.kyc.accountservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserMapper userMapper;

    UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userMapper = new UserMapperImpl(new ModelMapper());
        userService = new UserServiceImpl(userMapper, userRepository);
    }

    @Test
    void saveUser() {
        User user = User.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(Address.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        UserDto userDto = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(AddressDto.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userService.saveUser(userDto);
        assertEquals(2, user.getUserId());
    }

    @Test
    void mapToUserDto() {
        User user = User.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(Address.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        UserDto userDto = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(AddressDto.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        UserDto mappedUserDto = userService.mapToUserDto(user);
        assertEquals(userDto.getUserId(), mappedUserDto.getUserId());
    }

    @Test
    void findByUserId() {
        User user = User.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(Address.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        when(userRepository.findByUserId(anyLong())).thenReturn(Optional.ofNullable(user));

        Optional<User> userOptional = userService.findByUserId(2l);
        assertEquals(2l, userOptional.get().getUserId());
    }
}