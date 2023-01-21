package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.exceptions.BadLoginException;
import de.frauas.userauth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@ResponseBody
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UserDto userDto) throws BadLoginException {
        return authService.authenticate(userDto);
    }

    @GetMapping("/refreshTokens")
    public Map<String, String> refreshTokens(@RequestHeader("refreshToken") String refreshToken) {
        return authService.refreshTokens(refreshToken);
    }
}
