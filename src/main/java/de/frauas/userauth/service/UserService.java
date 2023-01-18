package de.frauas.userauth.service;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.enums.RoleType;
import de.frauas.userauth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        //TODO: add salt to password
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        user.setRoles(List.of(RoleType.LESER));
        userRepository.save(user);
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
