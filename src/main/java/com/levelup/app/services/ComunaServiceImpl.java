package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.mappers.ComunaMapper;
import com.levelup.app.models.Comuna;
import com.levelup.app.models.dtos.ComunaDto;
import com.levelup.app.repositories.ComunaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComunaServiceImpl implements ComunaServices {

    private final ComunaRepository comunaRepository;
    private final ComunaMapper comunaMapper;

    @Transactional(readOnly = true)
    @Override
    public List<ComunaDto> findAll() {
        List<Comuna> comunas = (List<Comuna>) comunaRepository.findAll();
        return comunas.stream()
                .map(comunaMapper::toDto)
                .toList();
    }

}
