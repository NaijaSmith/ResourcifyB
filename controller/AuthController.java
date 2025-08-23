package com.resourcify.resourcify_backend.controller;

import com.resourcify.resourcify_backend.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        try {
            authService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
            return ResponseEntity.ok(Map.of(
                "message", "Registration successful!",
                "redirect", "http://127.0.0.1:5500/Services.html",
                "role", user.getRole()
                ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> signin(@RequestBody User loginUser) {
        try {
            authService.authenticateUser(loginUser.getUsername(), loginUser.getPassword());
            return ResponseEntity.ok(Map.of("message", "Login successful!", "redirect", "http://127.0.0.1:5500/Services.html"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found!"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Incorrect password!"));
        }
    }
}