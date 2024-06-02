package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.jwt.JwtService;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandler;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignInRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignInResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class SigninUsecaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ResponseHandler responseHandler;

    @InjectMocks
    private SignInRequest signInRequest;

    private SignInResponse signInResponse;

    private ResponseEntity responseEntity;

    @BeforeEach
    public void setUp() {
        signInRequest = new SignInRequest();
        signInResponse = new SignInResponse();
        responseEntity = ResponseEntity.ok(signInResponse);
    }
}
