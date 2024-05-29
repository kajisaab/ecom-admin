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

    @BeforeEach
    public void setUp() {
        request = new SignupRequest();
        request.setFirstName("Aman");
        request.setLastName("Khadka");
        request.setEmail("amankhadkakaji@gmail.com");
        request.setUserName("kajisaab1");
        request.setPhoneNumber("9999999999");
        request.setPassword("password");
    }

    @Test
    public void testExecute_Success() throws BadRequestException {
        when(userRepository.findByEmailAndUserName(anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userCredentialRepository.save(any(UserCredential.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(otpService.getOtp()).thenReturn(new GenerateOtpCodeDto(123456, null));
        doNothing().when(emailService).sendHtmlEmail(any(Context.class), anyString(), anyString());

        ResponseEntity<ResponseHandlerService> responseEntity = signupUsecase.execute(request);

        assertNotNull(responseEntity);
        SignupResponse response = responseEntity.getBody();
        assertNotNull(response);
        System.out.println("This is the response ============> " + response);

        verify(userRepository, times(2)).findByEmailAndUserName(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(otpSettingRepository, times(1)).save(any(OtpSetting.class));
        verify(emailService, times(1)).sendHtmlEmail(any(Context.class), anyString(), anyString());
    }
}


