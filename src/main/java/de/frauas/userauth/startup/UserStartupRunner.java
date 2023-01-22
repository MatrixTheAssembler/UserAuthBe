package de.frauas.userauth.startup;

import de.frauas.userauth.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserStartupRunner implements CommandLineRunner {

    private UserService userService;

    public UserStartupRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.createDefaultUser();
    }
}
