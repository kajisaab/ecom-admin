package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.emailconfig.EmailService;
import com.kajisaab.ecommerce.core.emailconfig.emailtemplate.EmailTemplates;
import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandler;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandlerService;
import com.kajisaab.ecommerce.feature.auth.entity.OtpSetting;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import com.kajisaab.ecommerce.feature.auth.repository.OtpSettingRepository;
import com.kajisaab.ecommerce.feature.auth.repository.UserCredentialRepository;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignupRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignupResponse;
import com.kajisaab.ecommerce.utils.GenerateOtpCodeDto;
import com.kajisaab.ecommerce.utils.OtpService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.context.Context;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SignupUsecaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private OtpSettingRepository otpSettingRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailTemplates emailTemplates;

    @Mock
    private OtpService otpService;

    @Mock
    private ResponseHandler responseHandler;

    @InjectMocks
    private SignupUsecase signupUsecase;

    private SignupRequest request;

    private SignupResponse signupResponse;

    private ResponseEntity response;

    @BeforeEach
    public void setUp() {
        request = new SignupRequest();
        request.setFirstName("Aman");
        request.setLastName("Khadka");
        request.setEmail("amankhadkakaji@gmail.com");
        request.setUserName("kajisaab1");
        request.setPhoneNumber("9999999999");
        request.setPassword("password");

        signupResponse = new SignupResponse("Successfully created account");

        response = new ResponseEntity( new ResponseHandlerService(HttpStatus.OK.value(), "SUCCESS", signupResponse), HttpStatus.OK);

    }

    @Test
    public void testExecute_Success() throws BadRequestException {
        when(userRepository.findByEmailAndUserName(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userCredentialRepository.save(any(UserCredential.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(otpService.getOtp()).thenReturn(new GenerateOtpCodeDto(123456, null));
        doNothing().when(emailService).sendHtmlEmail(any(Context.class), anyString(), anyString());

        ResponseEntity<SignupResponse> responseEntity = signupUsecase.execute(request);

        assertEquals(responseEntity, response);
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userRepository, times(2)).findByEmailAndUserName(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(otpSettingRepository, times(1)).save(any(OtpSetting.class));
        verify(emailService, times(1)).sendHtmlEmail(any(Context.class), anyString(), anyString());
    }

    @Test
    public void testExecute_EmailAlreadyExists(){
        // Stubbing for userRepository.findByEmailAndUserName
        when(userRepository.findByEmailAndUserName(anyString()))
                .thenAnswer(invocation -> {
                    String emailOrUsername = invocation.getArgument(0);
                    if (Objects.equals(emailOrUsername, request.getEmail())) {
                        return new User(); // Simulate user with the same email
                    } else {
                        return null; // Simulate user not found
                    }
                });

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            signupUsecase.execute(request);
        });

        assertEquals("User with amankhadkakaji@gmail.com email already exists", exception.getMessage());

        verify(userRepository, times(1)).findByEmailAndUserName(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userCredentialRepository, never()).save(any(UserCredential.class));
        verify(otpSettingRepository, never()).save(any(OtpSetting.class));
        verify(emailService, never()).sendHtmlEmail(any(Context.class), anyString(), anyString());
    }

    @Test
    public void testExecute_UsernameAlreadyExists() {
        // Stubbing for userRepository.findByEmailAndUserName
        when(userRepository.findByEmailAndUserName(anyString()))
                .thenAnswer(invocation -> {
                    String emailOrUsername = invocation.getArgument(0);
                    if (Objects.equals(emailOrUsername, request.getUserName())) {
                        return new User(); // Simulate user with the same username
                    } else {
                        return null; // Simulate user not found
                    }
                });

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            signupUsecase.execute(request);
        });

        assertEquals("User with kajisaab1 username already exists", exception.getMessage());

        verify(userRepository, times(2)).findByEmailAndUserName(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userCredentialRepository, never()).save(any(UserCredential.class));
        verify(otpSettingRepository, never()).save(any(OtpSetting.class));
        verify(emailService, never()).sendHtmlEmail(any(Context.class), anyString(), anyString());
    }

    @Test
    public void testExecute_InvalidRequest(){
        request.setEmail(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            signupUsecase.execute(request);
        });

        assertNotNull(exception);
    }
}


