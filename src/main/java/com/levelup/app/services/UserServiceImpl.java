package com.levelup.app.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.exception.NotFoundException;
import com.levelup.app.exception.UserAlreadyExist;
import com.levelup.app.mappers.UserMapper;
import com.levelup.app.models.Comuna;
import com.levelup.app.models.Role;
import com.levelup.app.models.User;
import com.levelup.app.models.dtos.UserDto;
import com.levelup.app.repositories.ComunaRepository;
import com.levelup.app.repositories.RoleRepository;
import com.levelup.app.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ComunaRepository comunaRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User save(UserDto userDto) {
        Comuna comuna = comunaRepository.findById(userDto.getComunaId())
                .orElseThrow(() -> new NotFoundException("La comuna no existe!"));
        
        Role role;
        if (userDto.getRole() == null) {
            role = roleRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundException("El rol por defecto no existe!"));
        } else {
            role = roleRepository.findById(userDto.getRole())
                    .orElseThrow(() -> new NotFoundException("El rol solicitado no existe!"));
        }

        if (userRepository.findByRun(userDto.getRun()).isPresent()) {
            throw new UserAlreadyExist("Este RUN ya esta registrado!");
        }

        if (userRepository.findUserByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExist("Este email ya esta registrado!");
        }

        User newUser = userMapper.toEntity(userDto);
        newUser.setComuna(comuna);
        newUser.setRole(role);
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllUserDto() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto findUserDtoById(Long id) {
        User userDb = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado!"));
        return userMapper.toDto(userDb);
    }

    @Transactional
    @Override
    public void destroy(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public User editUser(Long id, UserDto userDto) {
        User userDb = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Comuna comuna = comunaRepository.findById(userDto.getComunaId()).orElseThrow(
                () -> new NotFoundException("Comuna no encontrada!"));

        Role role = roleRepository.findById(userDto.getRole())
                .orElseThrow(() -> new NotFoundException("El rol no existe!"));

        User userUpdate = userMapper.toEntity(userDto);
        userUpdate.setId(id);
        userUpdate.setComuna(comuna);
        userUpdate.setRole(role);
        
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            userUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        } else {
            userUpdate.setPassword(userDb.getPassword());
        }

        return userRepository.save(userUpdate);
    }
}