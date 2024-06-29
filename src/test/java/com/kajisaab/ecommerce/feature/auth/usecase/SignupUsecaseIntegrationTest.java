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
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
    public void testExecute_UserAlreadyRegistered() throws BadRequestException {
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

        // Flush and clear the EntityManager to ensure data is written to the database
//        entityManager.flush();
//        entityManager.clear();

        // Verify the data is actually saved
        User savedUser = userRepository.findByEmailAndUserName(request.getUserName());
        System.out.println("Success");

//        assertNotNull(savedUser);
//        System.out.println(savedUser);
//        assertEquals(savedUser.get().getEmail(), request.getEmail());
    }
}
