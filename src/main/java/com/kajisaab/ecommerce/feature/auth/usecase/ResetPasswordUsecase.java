package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.responsehandler.ResponseHandler;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.core.validation.ValidationUtils;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import com.kajisaab.ecommerce.feature.auth.repository.UserCredentialRepository;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.ResetPasswordRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.ResetPasswordResponse;
import com.kajisaab.ecommerce.utils.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class ResetPasswordUsecase implements Usecase<ResetPasswordRequest, ResetPasswordResponse> {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public ResponseEntity<ResetPasswordResponse> execute(ResetPasswordRequest request) throws BadRequestException {
        String violations = ValidationUtils.validate(request);
        if (!Objects.isNull(violations)) {
            throw new BadRequestException(violations);
        }

        UserDetails userDetails = userService.getAuthenticatedUser();

        User user = userRepository.findByEmailAndUserName(userDetails.getUsername());

        List<String> passwordHistory = user.getUserCredential().getPasswordHistory();

        AtomicBoolean isAlreadyUsed = new AtomicBoolean(false);

        List<String> newPasswordHistory = new ArrayList<>();

        if (!passwordHistory.isEmpty()) {
            for (int i = 0; i < passwordHistory.size(); i++) {
                String pass = passwordHistory.get(i);
                if (i < 2) {
                    newPasswordHistory.set(i, pass);
                }
                // Compare if any
                if (i < 3 && passwordEncoder.matches(request.getPassword(), pass)) {
                    isAlreadyUsed.set(true);
                    break; // Assuming you want to stop once you find a match
                }
            }

        }

        if (isAlreadyUsed.get()) {
            throw new BadRequestException("Password already used");
        }

        // Add the new password to the first index of the password history and maintain only 3 history
        newPasswordHistory.set(0, passwordEncoder.encode(request.getPassword()));

        UserCredential userCred = user.getUserCredential();

        // update the user password.
        userCred.setPassword(passwordEncoder.encode((request.getPassword())));

        // set the password history to the entity
        userCred.setPasswordHistory(newPasswordHistory);

        // save the updated the user credential to the database.
        userCredentialRepository.save(userCred);

        ResetPasswordResponse response = new ResetPasswordResponse("Successfully updated password");
        return ResponseHandler.responseBuilder(response);
    }
}
