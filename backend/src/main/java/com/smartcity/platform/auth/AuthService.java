package com.smartcity.platform.auth;

import com.smartcity.platform.common.BadRequestException;
import com.smartcity.platform.common.ResourceNotFoundException;
import com.smartcity.platform.security.JwtTokenProvider;
import com.smartcity.platform.user.Role;
import com.smartcity.platform.user.User;
import com.smartcity.platform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public JwtAuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String token = jwtTokenProvider.generateToken(authentication);
        return new JwtAuthResponse(token, "Bearer", user.getUsername(), user.getRoles().iterator().next().name());
    }

    @Transactional
    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email already exists");
        }

        Set<Role> roles = request.roles() == null || request.roles().isEmpty()
                ? Set.of(Role.CITIZEN)
                : request.roles();

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
