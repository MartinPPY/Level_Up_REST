package com.levelup.app.controller;

import com.levelup.app.models.dtos.UserDto;
import com.levelup.app.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAlluser() {
        return ResponseEntity.ok().body(userService.findAllUserDto());

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok().body(userService.findUserDtoById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto user, BindingResult result) {

        if (result.hasErrors()) {
            return validation(result);
        }

        userService.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro exitoso!");
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        userService.editUser(id, userDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Cambios guardados!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.destroy(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Usuario eliminado!");
        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            response.put(error.getField(), "El campo " + error.getField() + " es invalido!");
        });

        return ResponseEntity.badRequest().body(response);

    }

}
