package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.exceptions.UserAlreadyCreatedException;
import de.frauas.userauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@RequestBody UserDto userDto) throws UserAlreadyCreatedException {

        User existingUser = userService.findUserByUserName(userDto.getUsername());

        if (existingUser != null) {
            throw new UserAlreadyCreatedException();
        }

        userService.saveUser(userDto);
    }
}
