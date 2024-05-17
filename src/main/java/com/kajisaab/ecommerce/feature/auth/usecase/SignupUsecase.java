package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.constants.UserRoleConstantEnum;
import com.kajisaab.ecommerce.constants.UserTypeConstantEnum;
import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.responseHandler.ResponseHandler;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.core.validation.ValidationUtils;
import com.kajisaab.ecommerce.feature.auth.entity.User;
import com.kajisaab.ecommerce.feature.auth.entity.UserCredential;
import com.kajisaab.ecommerce.feature.auth.repository.UserCredentialRepository;
import com.kajisaab.ecommerce.feature.auth.repository.UserRepository;
import com.kajisaab.ecommerce.feature.auth.usecase.request.SignupRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.SignupResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SignupUsecase implements Usecase<SignupRequest, SignupResponse> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCredentialRepository userCredentialRepository;


    @Override
    @Transactional
    public ResponseEntity<SignupResponse> execute(SignupRequest request) throws BadRequestException {
        String violations = ValidationUtils.validate(request);
        if(!Objects.isNull(violations)){
            throw new BadRequestException(violations);
        }

        String emailValidation = isNewUser(request.getEmail(), true);
        String usernameValidation = isNewUser(request.getUserName(), false);

        if (emailValidation != null) {
            throw new BadRequestException(emailValidation);
        }

        if (usernameValidation != null) {
            throw new BadRequestException(usernameValidation);
        }

        try {
            var user = User
                    .builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .userName(request.getUserName())
                    .role(UserRoleConstantEnum.ADMIN.getName())
                    .userType(UserTypeConstantEnum.VENDOR.getName())
                    .phoneNumber(request.getPhoneNumber())
                    .build();

            User savedUser = userRepository.save(user);

            UserCredential userCredential = new UserCredential();
            userCredential.setUserDetails(savedUser);
            userCredential.setPasswordHistory(Collections.EMPTY_LIST);
            userCredential.setPassword(passwordEncoder.encode((request.getPassword())));

            System.out.println("userCredential.toString() = " + userCredential.toString());
            userCredentialRepository.save(userCredential);

//        GenerateOtpCodeDto generatedOtp = this.otpService.getOtp();
//        var otpDetails = OtpSetting.builder().userCredential(savedCred).otp(generatedOtp.getOtpCode()).expiry_date_time(generatedOtp.getExpiryTime()).build();
//        otpSettingRepository.save(otpDetails);
//            emailService.sendHtmlEmail(generatedOtp.getOtpCode(), request.getEmail());


            SignupResponse response = new SignupResponse("Successfully created account");
            return ResponseHandler.responseBuilder(response);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public String isNewUser(String emailOrUsername, boolean isEmail) {
        User user = userRepository.findByEmailAndUserName(emailOrUsername);
        if(user != null){
            return "User with " + emailOrUsername + (isEmail ? " email " : " username ") + "already exists";
        };
        return null;
    }
}
