package com.levelup.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levelup.app.models.Product;
import com.levelup.app.models.dtos.ProductDto;
import com.levelup.app.services.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllproduct() {
        return ResponseEntity.ok().body(productService.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getProductById(@PathVariable String code) {
        return ResponseEntity.ok().body(productService.findProductDto(code));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDto productDto) {
        Product newProduct = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(newProduct);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> edit(@PathVariable String code, @Valid @RequestBody ProductDto productDto,
            BindingResult result) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Map<String, Object> response = new HashMap<>();
        productService.editProduct(code, productDto);

        response.put("message", "Producto editado!");

        return ResponseEntity.ok().body(null);

    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteProduct(@PathVariable String code) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", "Producto eliminado");

        productService.deleteById(code);
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