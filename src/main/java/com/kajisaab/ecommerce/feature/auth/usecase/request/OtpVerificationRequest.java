package com.kajisaab.ecommerce.feature.auth.usecase.request;

import com.kajisaab.ecommerce.core.usecase.UsecaseRequest;
import com.kajisaab.ecommerce.feature.auth.dto.OtpVerificationRequestBodyDto;
import lombok.Data;

@Data
public class OtpVerificationRequest implements UsecaseRequest {
    private String userId;
    private String otp;

    public OtpVerificationRequest(OtpVerificationRequestBodyDto request){
        this.userId = request.userId();
        this.otp = request.otp();
    }

}
