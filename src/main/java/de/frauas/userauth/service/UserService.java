package de.frauas.userauth.service;


import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByUserName(String userName);

    List<UserDto> findAllUsers();

}
