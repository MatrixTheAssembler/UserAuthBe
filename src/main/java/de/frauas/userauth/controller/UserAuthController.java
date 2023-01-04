package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserAuthController {

    private UserService userService;

    public UserAuthController(UserService userService) {
        this.userService = userService;
    }


    // TODO: Endpunkte auch in eigene Klassen auslagern?

    @PostMapping("/logout")
    public String logout(@Valid @ModelAttribute("user") UserDto userDto,
                         BindingResult result,
                         Model model) {
        // TODO
        return "ToDo";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users");
        return "users";
    }

    @GetMapping("/roles")
    public String roleList() {
        // TODO
        return "ToDo";
    }

    @PostMapping("/roles")
    public String updateRoleList() {
        // TODO
        return "ToDo";
    }


}
