package com.kyc.accountservice.mapper;

import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.UserDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {

    private ModelMapper modelMapper;

    @Override
    public User userDtoToUser(UserDto userDto) {
        if (null == userDto){
            return null;
        }
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto userToUserDto(User user) {
        if (null == user){
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }
}
