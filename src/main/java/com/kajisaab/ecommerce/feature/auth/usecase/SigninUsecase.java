package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.jwt.JwtService;
import com.kajisaab.ecommerce.core.responseHandler.ResponseHandler;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.core.validation.ValidationUtils;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SigninRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SigninResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SigninUsecase implements Usecase<SigninRequest, SigninResponse> {

    @Autowired
    private Environment env;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<SigninResponse> execute(SigninRequest request) {
        String violations = ValidationUtils.validate(request);
        if (!Objects.isNull(violations)) {
            throw new BadRequestException(violations);
        }

        boolean isEmail;
        isEmail = request.getEmailOrUsername().contains("@") && request.getEmailOrUsername().contains(".");
        User user = userRepository.findByEmailAndUserName(request.getEmailOrUsername());


        if(user == null){
            throw new BadRequestException("User with " + request.getEmailOrUsername() + (isEmail ? " email " : " username ") + "not found");
        }

        String accessJwtToken = "";

        String refreshJwtToken = "";
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid Credentials ");
        }

        if (!user.isActive()) {
            throw new BadRequestException("User is disabled");
        }

        accessJwtToken  = jwtService.generateToken(user);
        refreshJwtToken = jwtService.generateToken(user, env.getProperty("spring.env.token.refreshSecretKey"), 2592000000L);


        SigninResponse response = new SigninResponse(accessJwtToken, refreshJwtToken);
        return ResponseHandler.responseBuilder(response);
    }
}
