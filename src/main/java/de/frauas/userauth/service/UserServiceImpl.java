package de.frauas.userauth.service;

import de.frauas.userauth.dto.UserDto;
import de.frauas.userauth.entity.Role;
import de.frauas.userauth.entity.User;
import de.frauas.userauth.repository.RoleRepository;
import de.frauas.userauth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_BASIC");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user.getUsername());
                    return userDto;
                })
                .collect(Collectors.toList());
    }


    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_BASIC");
        return roleRepository.save(role);
    }
}
