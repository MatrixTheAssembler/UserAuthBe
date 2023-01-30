package de.frauas.userauth.config;

import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.filter.AuthTokenFilter;
import de.frauas.userauth.service.UserDetailsServiceImpl;
import de.frauas.userauth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .authorizeHttpRequests(authorize ->
                        authorize
                                //RegisterController
                                .requestMatchers(HttpMethod.POST, "/register").permitAll()

                                // AuthController
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/refreshTokens").permitAll()

                                // UserController
                                .requestMatchers(HttpMethod.GET, "/users").hasAuthority(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/users/roles").hasAuthority(RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyAuthority(RoleType.LESER.name(),
                                        RoleType.AUTOR.name(), RoleType.MODERATOR.name(), RoleType.ADMIN.name())
                                .requestMatchers(HttpMethod.PUT, "/users/{username}").hasAuthority(RoleType.ADMIN.name())


                                // ArticleController
                                .requestMatchers(HttpMethod.GET, "/articles/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/articles").hasAuthority(RoleType.AUTOR.name())
                                .requestMatchers(HttpMethod.PUT, "/articles/{id}").hasAuthority(RoleType.AUTOR.name())
                                .requestMatchers(HttpMethod.DELETE, "/articles/{id}").hasAnyAuthority(RoleType.AUTOR.name()
                                        , RoleType.MODERATOR.name())

                                // CommentController
                                .requestMatchers(HttpMethod.POST, "/comment").hasAuthority(RoleType.LESER.name())
                                .requestMatchers(HttpMethod.PUT, "/comment").hasAuthority(RoleType.LESER.name())
                                .requestMatchers(HttpMethod.DELETE, "/comment/{articleId}/{commentId}").hasAnyAuthority(RoleType.LESER.name()
                                        , RoleType.MODERATOR.name())

                                // TestController
                                .requestMatchers("/test").hasAuthority(RoleType.ADMIN.name())

                                .requestMatchers("/error").permitAll() //Wird für Fehlermeldungen benötigt

                                .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthTokenFilter(jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        return request -> configuration;
    }
}
