package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.mapper.UserMapper;
import de.frauas.userauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@ResponseBody
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> users() {
        return UserMapper.toUserDtoList(userService.findAllUsers());
    }

    @GetMapping("/roles")
    public List<RoleType> roleList() {
        return List.of(RoleType.values());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> user(@PathVariable String username) {
        return ResponseEntity.ok(UserMapper.toUserDto(userService.findUserByUserName(username)));
    }

    @PutMapping("/{username}")
    public void updateRoles(@PathVariable String username, @RequestBody List<RoleType> roleList) {
        User existingUser = userService.findUserByUserName(username);
        userService.updateRoleList(existingUser, roleList);
    }
}
