package com.levelup.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levelup.app.models.Comuna;
import com.levelup.app.models.Region;
import com.levelup.app.services.ComunaServices;
import com.levelup.app.services.RegionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/locations")
@RequiredArgsConstructor
public class LocationsController {

    private ComunaServices comunaServices;

    private RegionService regionService;

    @GetMapping("/comunas")
    public ResponseEntity<List<Comuna>> getAllComunas() {
        return ResponseEntity.ok().body(comunaServices.findAll());

    }

    @GetMapping("/regiones")
    public ResponseEntity<List<Region>> getAllRegions() {
        return ResponseEntity.ok().body(regionService.findAll());
    }

}
