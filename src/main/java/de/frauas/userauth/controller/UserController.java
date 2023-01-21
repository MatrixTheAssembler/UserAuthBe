package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.mapper.UserMapper;
import de.frauas.userauth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> users() {
        return UserMapper.toUserDtoList(userService.findAllUsers());
    }

    @GetMapping("/roles")
    public List<RoleType> roleList() {
        return List.of(RoleType.values());
    }

    @PutMapping("/{userName}")
    public void updateRoles(@PathVariable String userName, @RequestBody List<RoleType> roleList) {
        User existingUser = userService.findUserByUserName(userName);
        userService.updateRoleList(existingUser, roleList);
    }


}
