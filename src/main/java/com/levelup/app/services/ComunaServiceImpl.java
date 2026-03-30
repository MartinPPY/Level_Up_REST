package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.models.Comuna;
import com.levelup.app.repositories.ComunaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComunaServiceImpl implements ComunaServices {

        
    private ComunaRepository comunaRepository;

    @Transactional
    @Override
    public List<Comuna> findAll() {
        List<Comuna> comunas = (List<Comuna>) comunaRepository.findAll();
        return comunas;
    }

}
