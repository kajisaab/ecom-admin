package com.kajisaab.ecommerce.feature.auth.usecase.response;

import com.kajisaab.ecommerce.core.usecase.UsecaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResetPasswordResponse implements UsecaseResponse {
    private String message;
}
