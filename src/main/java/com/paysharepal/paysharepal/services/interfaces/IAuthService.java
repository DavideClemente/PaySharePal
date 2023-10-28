package com.paysharepal.paysharepal.services.interfaces;

import com.paysharepal.paysharepal.infrastructure.dto.contracts.LoginContract;
import com.paysharepal.paysharepal.infrastructure.dto.contracts.UserContract;
import com.paysharepal.paysharepal.infrastructure.dto.responses.AuthenticationDto;

public interface IAuthService {
    AuthenticationDto register(UserContract request);
    AuthenticationDto authenticate(LoginContract request);
}
