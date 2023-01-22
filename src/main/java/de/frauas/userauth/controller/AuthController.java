package de.frauas.userauth.controller;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.service.AuthService;
import org.springframework.http.ResponseEntity;
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
    public Map<String, String> login(@RequestBody UserDto userDto) {
        return authService.authenticate(userDto);
    }

    @GetMapping("/refreshTokens")
    public ResponseEntity<Map<String, String>> refreshTokens(@RequestHeader("Authorization") String header) {
        return ResponseEntity.ok(authService.refreshTokens(header));
    }
}
