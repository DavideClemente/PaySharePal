package com.paysharepal.paysharepal.services;

import com.paysharepal.paysharepal.config.JWTService;
import com.paysharepal.paysharepal.infrastructure.dto.contracts.LoginContract;
import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.AuthenticationDto;
import com.paysharepal.paysharepal.model.Role;
import com.paysharepal.paysharepal.model.User;
import com.paysharepal.paysharepal.repositories.IUserRepository;
import com.paysharepal.paysharepal.services.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationDto register(UserContract request) {
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .roles(List.of(Role.USER))
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationDto.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationDto authenticate(LoginContract request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        // Authenticated
        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationDto.builder()
                .token(jwtToken)
                .build();
    }
}
