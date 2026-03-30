package com.levelup.app.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.levelup.app.models.User;
import com.levelup.app.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    
    private UserRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userDb = repository.findUserByEmail(email);

        if (!userDb.isPresent()) {
            throw new UsernameNotFoundException(String.format("Email %s no existe en el sistema", email));
        }

        User user = userDb.orElseThrow();

        List<GrantedAuthority> authorities = Arrays.asList(user.getRole()).stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), // se compara el email
                user.getPassword(), // se compara la password
                true,
                true,
                true,
                true,
                authorities);
    }
    
}
