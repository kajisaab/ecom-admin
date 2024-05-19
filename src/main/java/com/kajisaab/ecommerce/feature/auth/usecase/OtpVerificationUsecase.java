package com.kajisaab.ecommerce.feature.auth.usecase;

import com.kajisaab.ecommerce.core.exception.BadRequestException;
import com.kajisaab.ecommerce.core.usecase.Usecase;
import com.kajisaab.ecommerce.feature.auth.usecase.request.OtpVerificationRequest;
import com.kajisaab.ecommerce.feature.auth.usecase.response.OtpVerificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpVerificationUsecase implements Usecase<OtpVerificationRequest, OtpVerificationResponse> {

    @Override
    public ResponseEntity<OtpVerificationResponse> execute(OtpVerificationRequest request) throws BadRequestException {
        return null;
    }
}
