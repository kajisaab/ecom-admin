package com.kajisaab.ecommerce.feature.auth;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.feature.auth.dto.ForgotPasswordRequestBodyDto;
import com.kajisaab.ecommerce.feature.auth.dto.PasswordResetRequestBodyDto;
import com.kajisaab.ecommerce.feature.auth.dto.SignInRequestParamDto;
import com.kajisaab.ecommerce.feature.auth.dto.SignupRequestBodyDto;
import com.kajisaab.ecommerce.feature.auth.usecase.*;
import com.kajisaab.ecommerce.feature.auth.usecase.request.*;
import com.kajisaab.ecommerce.feature.auth.usecase.response.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final SigninUsecase signinUsecase;
    private final SignupUsecase signupUsecase;
    private final SignoutUsecase signoutUsecase;
    private final ResetPasswordRequestUsecase resetPasswordRequestUsecase;
    private final ResetPasswordUsecase resetPasswordUsecase;

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> getSignInInformation(@RequestBody SignInRequestParamDto request) throws BadRequestException {
        SignInRequest req = new SignInRequest(request);
        return this.signinUsecase.execute(req);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignupResponse> onboardUser(@RequestBody SignupRequestBodyDto request) throws BadRequestException{
        SignupRequest req = new SignupRequest(request);
        return this.signupUsecase.execute(req);
    }

    @GetMapping("/sign-out")
    public ResponseEntity<SignOutResponse> logoutUser() throws BadRequestException {
        SignoutRequest req = new SignoutRequest();
        return this.signoutUsecase.execute(req);
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<RequestPasswordResetResponse> passwordResetRequest(HttpServletRequest httprequest, @RequestBody ForgotPasswordRequestBodyDto request) throws BadRequestException{
        String protocol = httprequest.getScheme(); // http or https
        String host = httprequest.getHeader("host"); // Hostname with port, e.g., example.com:8080
        RequestPasswordResetRequest req = new RequestPasswordResetRequest(request, protocol, host);
        return this.resetPasswordRequestUsecase.execute(req);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody PasswordResetRequestBodyDto request)throws BadRequestException{
        ResetPasswordRequest req = new ResetPasswordRequest(request);
        return this.resetPasswordUsecase.execute(req);
    }


}
