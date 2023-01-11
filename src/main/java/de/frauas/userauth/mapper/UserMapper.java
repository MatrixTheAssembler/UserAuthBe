package de.frauas.userauth.mapper;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toUser(UserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

    public static List<User> toUserList(List<UserDto> userDtos){
        return userDtos.stream()
                .map(userDto -> User.builder()
                        .username(userDto.getUsername())
                        .password(userDto.getPassword())
                        .build())
                .collect(Collectors.toList());
    }

    public static UserDto toUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public static List<UserDto> toUserDtoList(List<User> users) {
        return users.stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .roles(user.getRoles())
                        .build())
                .collect(Collectors.toList());
    }
}
