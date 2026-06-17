package org.example.controller;

import org.example.domain.User;
import org.example.dto.LoginRequest;
import org.example.security.JwtUtil;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil)
    {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        User authenticatedUser = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        if (authenticatedUser != null)
        {
            String token = jwtUtil.generateToken(authenticatedUser.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
