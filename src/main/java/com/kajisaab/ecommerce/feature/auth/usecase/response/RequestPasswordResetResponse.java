package com.kajisaab.ecommerce.feature.auth.usecase.response;

import com.kajisaab.ecommerce.core.usecase.UsecaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestPasswordResetResponse implements UsecaseResponse {
    private String message;
}
