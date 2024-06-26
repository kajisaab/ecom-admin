package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.jwt.JwtService;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandler;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandlerService;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignInRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignInResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SigninUsecaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private Environment env;

    @Mock
    private ResponseHandler responseHandler;

    @InjectMocks
    private SigninUsecase signinUsecase;

    private SignInRequest signInRequest;

    private SignInResponse signInResponse;

    private ResponseEntity response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
//        signInRequest = new SignInRequest();
//        signInRequest.setPassword("12345678");
//        signInRequest.setUserId("amankhadka101@gmail.com");
//
//        signInResponse = new SignInResponse("token", "refreshToken");
//
//        response = new ResponseEntity(new ResponseHandlerService(HttpStatus.OK.value(), "SUCCESS", signInResponse), HttpStatus.OK);
//
    }

    @Test
    public void testExecute_Success() throws BadRequestException{
        SignInRequest request = new SignInRequest();
        request.setUserId("amankhadka101@gmail.com");
        request.setPassword("123456789");
        System.out.println("This is the request" + request);
        // Act
        ResponseEntity<SignInResponse> responseEntity = signinUsecase.execute(request);

        System.out.println("This is the response Entity" + responseEntity);
        // Assert
        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getBody());
        System.out.println(responseEntity.getBody());
//        assertEquals("accessJwtToken", responseEntity.getBody().getAccessToken());
//        assertEquals("refreshJwtToken")

    }
}
