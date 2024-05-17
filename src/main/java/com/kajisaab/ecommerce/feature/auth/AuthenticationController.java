package com.kajisaab.ecommerce.feature.auth;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.feature.auth.dto.SigninRequestParamDto;
import com.kajisaab.ecommerce.feature.auth.dto.SignupRequestBodyDto;
import com.kajisaab.ecommerce.feature.auth.usecase.SigninUsecase;
import com.kajisaab.ecommerce.feature.auth.usecase.SignoutUsecase;
import com.kajisaab.ecommerce.feature.auth.usecase.SignupUsecase;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignoutRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SigninRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignupRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignoutResponse;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SigninResponse;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignupResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> getSignInInformation(@RequestBody SigninRequestParamDto request) throws BadRequestException {
        SigninRequest req = new SigninRequest(request);
        return this.signinUsecase.execute(req);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> onboardUser(@RequestBody SignupRequestBodyDto request) throws BadRequestException{
        SignupRequest req = new SignupRequest(request);
        return this.signupUsecase.execute(req);
    }

    @GetMapping("/sign-out")
    public ResponseEntity<SignoutResponse> logoutUser() throws BadRequestException {
        SignoutRequest req = new SignoutRequest();
        return this.signoutUsecase.execute(req);
    }
}
