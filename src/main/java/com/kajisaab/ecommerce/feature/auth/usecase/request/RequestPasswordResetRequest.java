package com.kajisaab.ecommerce.feature.auth.usecase.request;

import com.kajisaab.ecommerce.core.usecase.UsecaseRequest;
import com.kajisaab.ecommerce.feature.auth.dto.ForgotPasswordRequestBodyDto;
import lombok.Data;

@Data
public class RequestPasswordResetRequest implements UsecaseRequest {
    private String email;
    private String protocol;
    private String host;

    public RequestPasswordResetRequest(ForgotPasswordRequestBodyDto request, String protocol, String host) {
        this.email = request.email();
        this.protocol = protocol;
        this.host = host;
    }
}
