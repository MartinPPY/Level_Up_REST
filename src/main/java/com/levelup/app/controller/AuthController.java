package com.levelup.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levelup.app.models.dtos.UserDto;
import com.levelup.app.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto user, BindingResult result) {

        if (result.hasErrors()) {
            return validation(result);
        }

        userService.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro exitoso!");
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @GetMapping("/isadmin")
    public ResponseEntity<?> isAdmin(Authentication authentication){
        return ResponseEntity.ok(authentication.getAuthorities());

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            response.put(error.getField(), "El campo " + error.getField() + " es invalido!");
        });

        return ResponseEntity.badRequest().body(response);

    }

}
