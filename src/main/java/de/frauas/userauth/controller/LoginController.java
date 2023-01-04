package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/login")
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showLoginForm(Model model) {
        // TODO
        return "ToDo";
    }

    @PostMapping
    public String login(@Valid @ModelAttribute("user") UserDto userDto,
                        BindingResult result,
                        Model model) {
        // TODO
        return "ToDo";
    }


}
