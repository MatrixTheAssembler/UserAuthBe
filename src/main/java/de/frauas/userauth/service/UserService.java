package de.frauas.userauth.service;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.exceptions.UserAlreadyCreatedException;
import de.frauas.userauth.repository.UserRepository;
import de.frauas.userauth.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecurityUtil securityUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, SecurityUtil securityUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.securityUtil = securityUtil;
    }

    private void createUser(String username, String password, List<RoleType> roles) throws UserAlreadyCreatedException {
        User existingUser = findUserByUserName(username);
        if (existingUser != null) {
            throw new UserAlreadyCreatedException();
        }

        String salt = securityUtil.generateSalt();
        String pepper = securityUtil.getPepper();
        String newPassword = password + salt + pepper;

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(newPassword))
                .salt(salt)
                .roles(roles)
                .build();

        userRepository.save(user);
    }

    public void registerUser(UserDto userDto) throws UserAlreadyCreatedException {
        createUser(userDto.getUsername(), userDto.getPassword(), List.of(RoleType.LESER));
    }

    public void createDefaultUser() {
        try {
            createUser("admin", "admin", List.of(RoleType.LESER, RoleType.ADMIN));
            System.out.println("Default user created");
        } catch (UserAlreadyCreatedException e) {
            System.out.println("Default user already exists");
        }
    }

    public boolean checkPassword(String userDtoPassword, String userPassword) {
        return passwordEncoder.matches(userDtoPassword, userPassword);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void updateRoleList(User user, List<RoleType> roles) {
        user.setRoles(roles);
        userRepository.save(user);
    }
}
