package com.paysharepal.paysharepal.controllers;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.LoginContract;
import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.AuthenticationDto;
import com.paysharepal.paysharepal.services.AuthService;
import com.paysharepal.paysharepal.services.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("register")
    public ResponseEntity<AuthenticationDto> register(
            @RequestBody UserContract request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationDto> authenticate(
            @RequestBody LoginContract request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
