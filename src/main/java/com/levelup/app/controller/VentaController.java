package com.levelup.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levelup.app.models.Venta;
import com.levelup.app.models.dtos.VentaDto;
import com.levelup.app.services.VentaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> getAll() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createVenta(@RequestBody List<VentaDto> ventaDtos) {
        ventaService.createVenta(ventaDtos);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Venta creada exitosamente!");
        return ResponseEntity.ok().body(response);
    }

}
