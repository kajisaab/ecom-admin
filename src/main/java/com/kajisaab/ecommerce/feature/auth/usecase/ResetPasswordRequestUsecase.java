package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.emailconfig.EmailService;
import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.jwt.JwtService;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandler;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.core.validation.ValidationUtils;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.RequestPasswordResetRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.RequestPasswordResetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResetPasswordRequestUsecase implements Usecase<RequestPasswordResetRequest, RequestPasswordResetResponse> {

    @Autowired
    private Environment env;

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<RequestPasswordResetResponse> execute(RequestPasswordResetRequest request) throws BadRequestException {
        String violations = ValidationUtils.validate(request);
        if (!Objects.isNull(violations)) {
            throw new BadRequestException(violations);
        }

        User user = userRepository.findByEmailAndUserName(request.getEmail());

        if(user == null){
            throw new BadRequestException("User not found");
        }

        String refreshJwtToken = jwtService.generateToken(user, env.getProperty("spring.env.token.accessSecretKey"), 2592000000L);

        String resetLink = request.getProtocol() + "://" + request.getHost() + "/reset-password/" + refreshJwtToken;

        Context context = new Context();
        context.setVariable("name", user.getFirstName() + " " + user.getLastName());
        context.setVariable("resetLink", resetLink);

        System.out.println("This is the resetLink" + resetLink);

        emailService.sendHtmlEmail(context, user.getEmail(), "passwordResetEmailTemplate", "Reset password");

        RequestPasswordResetResponse response = new RequestPasswordResetResponse("Successfully sent email to change password");
        return ResponseHandler.responseBuilder(response);
    }
}
