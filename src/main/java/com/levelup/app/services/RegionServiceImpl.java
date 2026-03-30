package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.levelup.app.models.Region;
import com.levelup.app.repositories.RegionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {

    
    private RegionRepository regionRepository;

    @Override
    public List<Region> findAll() {
        return (List<Region>) regionRepository.findAll();
    }


    
}
