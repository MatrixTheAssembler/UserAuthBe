package de.frauas.userauth.service;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.exceptions.BadLoginException;
import de.frauas.userauth.util.JwtTokenUtil;
import de.frauas.userauth.util.SecurityUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserService userService;

    private final SecurityUtil securityUtil;

    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(UserService userService, SecurityUtil securityUtil, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.securityUtil = securityUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public Map<String, String> authenticate(UserDto userDto) throws BadLoginException {
        User user = userService.findUserByUserName(userDto.getUsername(), true);

        if(user == null)
            throw new BadLoginException();

        String userDtoPassword = userDto.getPassword();
        userDtoPassword += user.getSalt() + securityUtil.getPepper();

        if (!userService.checkPassword(userDtoPassword, user.getPassword()))
            throw new BadLoginException();

        String accessToken = jwtTokenUtil.generateAccessToken(user);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user);

        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
    }

    public Map<String, String> refreshTokens(String header) {
        String refreshToken = jwtTokenUtil.getTokenFromAuthorizationHeader(header);
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
        User user = userService.findUserByUserName(username, false);

        if(user == null)
            throw new UsernameNotFoundException("User not found");

        String accessToken = jwtTokenUtil.generateAccessToken(user);
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }
}
