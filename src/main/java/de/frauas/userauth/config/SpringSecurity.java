package de.frauas.userauth.config;

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

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationConfiguration authenticationConfiguration;


    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // TODO: Fortlaufend alle Endpunkte hier einfügen
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/register").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/users").hasAuthority("ADMIN")
                                .requestMatchers("/roles").hasAuthority("ADMIN")

                                .requestMatchers("/test").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.GET, "/article").hasAnyAuthority("AUTOR", "READER", "MODERATOR")
                                .requestMatchers(HttpMethod.POST, "/article").hasAuthority("AUTOR")
                                .requestMatchers(HttpMethod.PUT, "/article").hasAuthority("AUTOR")
                                .requestMatchers(HttpMethod.DELETE, "/article").hasAnyAuthority("AUTOR", "MODERATOR")

                                .requestMatchers(HttpMethod.GET, "/article/comment").hasAnyAuthority("AUTOR", "READER", "MODERATOR")
                                .requestMatchers(HttpMethod.POST, "/article/comment").hasAuthority("READER")
                                .requestMatchers(HttpMethod.PUT, "/article/comment").hasAuthority("READER")
                                .requestMatchers(HttpMethod.DELETE, "/article/comment").hasAnyAuthority("READER", "MODERATOR")
                                .anyRequest().authenticated()
                )
//                .addFilter(new AuthenticationFilter(jwtTokenUtil, authenticationManager(authenticationConfiguration)))
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
}
