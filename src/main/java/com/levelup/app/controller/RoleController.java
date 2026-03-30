package com.levelup.app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levelup.app.services.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok().body(roleService.findAll());

    }

}
