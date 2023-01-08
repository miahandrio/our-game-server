package com.ourgame.ourgameserver.ws.model.user;

import com.ourgame.ourgameserver.ws.controllers.dto.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public UserJpaEntity saveUser(User userDto) {
        UserJpaEntity user = new UserJpaEntity();
        user.setId(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setAuthorities(userDto.getAuthorities());
        System.out.println(user.getId() + " " + user.getPassword() + " " + user.getEmail());
        return userRepository.save(user);
    }


    public UserJpaEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}