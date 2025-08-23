package com.resourcify.resourcify_backend.controller;

import com.resourcify.resourcify_backend.model.User;
import com.resourcify.resourcify_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpSession; // Import HttpSession

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HttpSession httpSession; // Inject HttpSession

    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        return userRepository.save(user);
    }

    public void authenticateUser(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Incorrect password!");
        }
        httpSession.setAttribute("userRole", user.getRole()); // Store role in session
    }
}