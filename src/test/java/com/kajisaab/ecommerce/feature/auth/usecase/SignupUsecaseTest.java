package com.kajisaab.ecommerce.feature.auth.usecase;
import com.kajisaab.ecommerce.core.emailconfig.EmailService;
import com.kajisaab.ecommerce.core.emailconfig.emailtemplate.EmailTemplates;
import com.kajisaab.ecommerce.core.exception.BadRequestException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class SignupUsecaseTest{

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCredentialRepository userCredentialRepository;

    @Mock
    private OtpSettingRepository otpSettingRepository;

    @Mock
    private User user;

    @Value("${spring.env.token.expires-in}")
    private Long tokenExpiryTime;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailTemplates emailTemplates;

    @Mock
    private OtpService otpService;

    @InjectMocks
    private SignupUsecase signupUsecase;

    @InjectMocks
    private SignupRequest request;

    @InjectMocks
    private SignupResponse response;

    @BeforeEach
    public void setUp(){
        request = new SignupRequest();
        request.setFirstName("Aman");
        request.setLastName("Khadka");
        request.setEmail("amankhadka101@gmail.com");
        request.setUserName("");
        request.setPhoneNumber("9999999999");
        request.setPassword("password");
    }

    @Test
    public void testExecute_UserAlreadyRegistered() throws BadRequestException{
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(otpService.getOtp()).thenReturn(new GenerateOtpCodeDto(123456, LocalDateTime.ofInstant(Instant.now().plus(tokenExpiryTime, ChronoUnit.MILLIS), ZoneId.systemDefault())));
        doNothing().when(emailService).sendHtmlEmail(any(Context.class), anyString(), anyString());

        ResponseEntity<SignupResponse> responseEntity = signupUsecase.execute(request);
        assertNotNull(responseEntity);

        Object data = responseEntity.getBody();
        assertNotNull(data);

        ResponseHandlerService responseHandlerService = (ResponseHandlerService) data;
        assertNotNull(responseHandlerService);

        int status = responseHandlerService.getStatus();
        String message = responseHandlerService.getMessage();
        SignupResponse body = (SignupResponse) responseHandlerService.getData();

        assertEquals(status, 200);
        assertEquals(message, "SUCCESS");
        assertEquals(body.getMessage(), "Successfully created account");
    }

    @Test
    public void testExecute_EmailAlreadyExists(){
        when(userRepository.findByEmailAndUserName(anyString())).thenAnswer(invocation -> {
            String emailOrUsername = invocation.getArgument(0);
            if(Objects.equals(emailOrUsername, request.getEmail())){
                return new User();
            }else {
                return null;
            }
        });

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            signupUsecase.execute(request);
        });

        assertEquals("User with amankhadka101@gmail.com email already exists", exception.getMessage());
        verify(userRepository, times(1)).findByEmailAndUserName(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userCredentialRepository, never()).save(any(UserCredential.class));
        verify(otpSettingRepository, never()).save(any(OtpSetting.class));
        verify(emailService, never()).sendHtmlEmail(any(Context.class), anyString(), anyString());
    }

    @Test
    public void testExecute_InvalidEmailRequest(){
//        request.setEmail(null);
        request.setEmail("");
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            signupUsecase.execute(request);
        });

//        assertEquals("Email cannot be null", exception.getMessage());
        assertEquals("Email cannot be empty", exception.getMessage());
        verify(userRepository, times(0)).findByEmailAndUserName(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userCredentialRepository, never()).save(any(UserCredential.class));
        verify(otpSettingRepository, never()).save(any(OtpSetting.class));
        verify(emailService, never()).sendHtmlEmail(any(Context.class), anyString(), anyString());
    }






}
