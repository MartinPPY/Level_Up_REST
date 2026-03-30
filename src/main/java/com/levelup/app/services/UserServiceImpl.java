package com.levelup.app.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.exception.NotFoundException;
import com.levelup.app.exception.UserAlreadyExist;
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

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private ComunaRepository comunaRepository;

    private RoleRepository roleRepository;

    @Transactional
    @Override
    public User save(UserDto user) {

        Comuna comuna = comunaRepository.findById(user.getComunaId().longValue())
                .orElseThrow(() -> new NotFoundException("La comuna no existe!"));
        Role role = null;

        if (user.getRole() == null) {

            role = roleRepository.findById(1L)
                    .orElseThrow(() -> new NotFoundException("El rol por defecto no existe!"));

        } else {
            role = roleRepository.findById(user.getRole().longValue())
                    .orElseThrow(() -> new NotFoundException("El rol por defecto no existe!"));
        }

        if (userRepository.findByRun(user.getRun()).isPresent()) {
            throw new UserAlreadyExist("Este RUN ya esta registrado!");
        }

        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExist("Este email ya esta registrado!");
        }

        LocalDate birthday = null;

        if (user.getBirthday() != null && !user.getBirthday().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthday = LocalDate.parse(user.getBirthday(), formatter);
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        User newUser = new User(
                user.getRun(),
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                birthday,
                encodedPassword,
                user.getAddres(),
                comuna,
                role);

        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllUserDto() {
        List<UserDto> users = new ArrayList<>();

        for (User user : userRepository.findAll()) {

            String birthdayStr = null;
            if (user.getBirthday() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                birthdayStr = user.getBirthday().format(formatter);
            }

            UserDto userDto = new UserDto(
                    user.getRun(),
                    user.getName(),
                    user.getLastname(),
                    user.getEmail(),
                    birthdayStr,
                    user.getAddres(),
                    user.getComuna().getId(),
                    user.getRole().getId(),
                    user.getId());
            users.add(userDto);

        }

        return users;

    }

    @Transactional(readOnly = true)
    @Override
    public UserDto findUserDtoById(Long id) {
        User userDb = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado!"));

        String birthdayStr = null;
        if (userDb.getBirthday() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthdayStr = userDb.getBirthday().format(formatter);
        }

        UserDto userDto = new UserDto(
                userDb.getRun(),
                userDb.getName(),
                userDb.getLastname(),
                userDb.getEmail(),
                birthdayStr,
                userDb.getAddres(),
                userDb.getComuna().getId(),
                userDb.getRole().getId(),
                userDb.getId());

        return userDto;

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

        Comuna comuna = comunaRepository.findById(userDto.getComunaId().longValue()).orElseThrow(
                () -> new NotFoundException("Comuna no encontrada!"));

        userDb.setComuna(comuna);

        Role role = roleRepository.findById(userDto.getRole().longValue())
                .orElseThrow(() -> new NotFoundException("El rol no existe!"));

        userDb.setRole(role);

        LocalDate birthday = null;

        if (userDto.getBirthday() != null && !userDto.getBirthday().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birthday = LocalDate.parse(userDto.getBirthday(), formatter);
        }

        userDb.setBirthday(birthday);

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        userDb.setRun(userDto.getRun());
        userDb.setName(userDto.getName());
        userDb.setLastname(userDto.getLastname());
        userDb.setEmail(userDto.getEmail());
        userDb.setPassword(encodedPassword);
        userDb.setAddres(userDto.getAddres());

        return userRepository.save(userDb);
    }
}