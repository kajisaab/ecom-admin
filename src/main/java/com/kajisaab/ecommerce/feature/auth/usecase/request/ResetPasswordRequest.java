package com.kajisaab.ecommerce.feature.auth.usecase.request;

import com.kajisaab.ecommerce.core.usecase.UsecaseRequest;
import com.kajisaab.ecommerce.feature.auth.dto.PasswordResetRequestBodyDto;
import lombok.Data;

@Data
public class ResetPasswordRequest implements UsecaseRequest {
    private String password;

    public ResetPasswordRequest(PasswordResetRequestBodyDto request) {
        this.password = request.password();
    }
}
