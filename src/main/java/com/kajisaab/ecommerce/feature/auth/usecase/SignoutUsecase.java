package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.responseHandler.ResponseHandler;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignoutRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignoutResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignoutUsecase implements Usecase<SignoutRequest, SignoutResponse> {

    private final UserRepository userRepository;
    @Override
    public ResponseEntity<SignoutResponse> execute(SignoutRequest request) throws BadRequestException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal());
        userRepository.updateRefreshToken(null, "123123");
        return ResponseHandler.responseBuilder(new SignoutResponse("Successfully signed out"));
    }
}
