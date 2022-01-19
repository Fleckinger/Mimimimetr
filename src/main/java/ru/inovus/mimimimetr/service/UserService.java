package ru.inovus.mimimimetr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.repository.UserRepository;
import ru.inovus.mimimimetr.security.UserDetailsServiceImpl;

import javax.transaction.Transactional;


@Service
@Transactional //TODO разобраться
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public User registerNewUser(User user) {

        if (emailExists(user.getEmail())) {
            throw new UserAlreadyExistsException("Данный email занят");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User getCurrentUser() {
        return userDetailsService.getCurrentAuthenticatedUser();
    }

}
