package de.frauas.userauth.filter;

import de.frauas.userauth.exceptions.UnauthorizedException;
import de.frauas.userauth.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (uriMatches(request, "/login")
                || uriMatches(request, "/refreshTokens")
                || uriMatches(request, "/register")
                || uriMatches(request, "/articles.*", "GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = jwtTokenUtil.getTokenFromRequest(request);

            if (token == null || !jwtTokenUtil.validateToken(token)) {
                System.out.println("Unauthorized");
                throw new UnauthorizedException();
            }

            List<SimpleGrantedAuthority> authorities = Arrays.stream(jwtTokenUtil.getRolesFromToken(token))
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            // Tells Spring which roles the current user has
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    jwtTokenUtil.getUsernameFromToken(token), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean uriMatches(HttpServletRequest request, String pattern) {
        return uriMatches(request, pattern, null);
    }

    private boolean uriMatches(HttpServletRequest request, String pattern, String httpMethod) {
        return httpMethod == null ? request.getRequestURI().matches(pattern) :
                request.getRequestURI().matches(pattern) && request.getMethod().equals(httpMethod);
    }
}
