package com.levelup.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.levelup.app.models.Role;
import com.levelup.app.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles;
    }

}
