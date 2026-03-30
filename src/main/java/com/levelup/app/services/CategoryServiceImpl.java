package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.levelup.app.models.Category;
import com.levelup.app.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {        
        return (List<Category>) categoryRepository.findAll();
    }


    
}
