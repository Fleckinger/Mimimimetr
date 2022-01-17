package ru.inovus.mimimimetr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.repository.UserRepository;

import javax.transaction.Transactional;


@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
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

    @Transactional
    public void update(User user) {

    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
