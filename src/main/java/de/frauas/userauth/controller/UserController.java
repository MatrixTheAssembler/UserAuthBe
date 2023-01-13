package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.mapper.UserMapper;
import de.frauas.userauth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // TODO: Endpunkte auch in eigene Klassen auslagern?

    @GetMapping("/users")
    public List<UserDto> users() {
        return UserMapper.toUserDtoList(userService.findAllUsers());
    }

    @GetMapping("/roles")
    public List<RoleType> roleList() {
        return List.of(RoleType.values());
    }


}
