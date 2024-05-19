package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandler;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignoutRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignOutResponse;
import com.kajisaab.ecommerce.utils.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignoutUsecase implements Usecase<SignoutRequest, SignOutResponse> {

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public ResponseEntity<SignOutResponse> execute(SignoutRequest request) throws BadRequestException {

        UserDetails userDetails = userService.getAuthenticatedUser();

        userRepository.updateRefreshToken(null, userDetails.getUsername());

        return ResponseHandler.responseBuilder(new SignOutResponse("Successfully signed out"));
    }
}
