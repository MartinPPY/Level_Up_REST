package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.mappers.RegionMapper;
import com.levelup.app.models.Region;
import com.levelup.app.models.dtos.RegionDto;
import com.levelup.app.repositories.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Transactional(readOnly = true)
    @Override
    public List<RegionDto> findAll() {
        List<Region> regiones = (List<Region>) regionRepository.findAll();
        return regiones.stream()
                .map(regionMapper::toDto)
                .toList();
    }

}
