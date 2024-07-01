package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.emailconfig.EmailService;
import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandlerService;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import com.kajisaab.ecommerce.feature.auth.repository.UserCredentialRepository;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignupRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignupResponse;
import com.kajisaab.ecommerce.utils.OtpService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.context.Context;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
@ActiveProfiles("integration")
public class SignupUsecaseIntegrationTest {

    @Autowired
    private SignupUsecase signupUsecase;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialRepository userCredentialRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OtpService otpService;

    @MockBean
    private EmailService emailService;

    private EntityManager entityManager;

    @Value("${spring.env.token.expires-in}")
    private Long tokenExpiryTime;

    private SignupRequest request;

    private SignupResponse response;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test1_Execute_SignupUserSuccessfully() throws BadRequestException {
        doNothing().when(emailService).sendHtmlEmail(any(Context.class), anyString(), anyString());

        // Execute the use case
        request = new SignupRequest();
        request.setFirstName("Aman");
        request.setLastName("Khadka");
        request.setEmail("amankhadkakaji@gmail.com");
        request.setUserName("");
        request.setPhoneNumber("9999999999");
        request.setPassword("password");
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

        // Verify the data is actually saved
        User savedUser = userRepository.findByEmailAndUserName(request.getUserName());
        assertNotNull(savedUser);
    }


    @Test
    public void test2_Execute_UserAlreadyExists() throws BadRequestException{
        doNothing().when(emailService).sendHtmlEmail(any(Context.class), anyString(), anyString());
        request = new SignupRequest();
        request.setFirstName("Aman");
        request.setLastName("Khadka");
        request.setEmail("amankhadkakaji@gmail.com");
        request.setUserName("");
        request.setPhoneNumber("9999999999");
        request.setPassword("password");

        // Execute the method and expect an exception
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            signupUsecase.execute(request);
        });

        System.out.println("This is the exception thrown " + thrown);
        // Verify the exception message
//        assertEquals("User with amankhadkakaji@gmail.com email already exists", thrown.getMessage());

    }


}
