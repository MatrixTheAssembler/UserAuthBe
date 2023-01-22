package de.frauas.userauth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 60 * 1000; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .withArrayClaim("roles", user.getRoles()
                        .stream()
                        .map(RoleType::toString)
                        .toArray(String[]::new))
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getSubject();
    }

    public String[] getRolesFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getClaim("roles")
                .asArray(String.class);
    }

    public boolean isTokenExpired(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token)
                .getExpiresAt()
                .before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return getTokenFromAuthorizationHeader(header);
    }

    public String getTokenFromAuthorizationHeader(String header){
        String prefix = "Bearer ";
        if (header != null && header.startsWith(prefix)) {
            return header.substring(prefix.length());
        }
        return null;
    }
}
