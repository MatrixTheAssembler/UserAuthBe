package de.frauas.userauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetailsService userDetailsService;

    public SpringSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // TODO: Fortlaufend alle Endpunkte hier einfügen
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/register").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/users").hasRole("ADMIN")
                                .requestMatchers("/roles").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/article").hasAnyRole("AUTHOR", "READER", "MODERATOR")
                                .requestMatchers(HttpMethod.POST, "/article").hasRole("AUTHOR")
                                .requestMatchers(HttpMethod.PUT, "/article").hasRole("AUTHOR")
                                .requestMatchers(HttpMethod.DELETE, "/article").hasAnyRole("AUTHOR", "MODERATOR")

                                .requestMatchers(HttpMethod.GET, "/article/comment").hasAnyRole("AUTHOR", "READER", "MODERATOR")
                                .requestMatchers(HttpMethod.POST, "/article/comment").hasRole("READER")
                                .requestMatchers(HttpMethod.PUT, "/article/comment").hasRole("READER")
                                .requestMatchers(HttpMethod.DELETE, "/article/comment").hasAnyRole("READER", "MODERATOR")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                //.defaultSuccessUrl("/users")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
