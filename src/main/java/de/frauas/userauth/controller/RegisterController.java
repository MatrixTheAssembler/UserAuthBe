package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.exceptions.UserAlreadyCreatedException;
import de.frauas.userauth.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@RequestBody UserDto userDto) throws UserAlreadyCreatedException {
        userService.registerUser(userDto);
    }
}
