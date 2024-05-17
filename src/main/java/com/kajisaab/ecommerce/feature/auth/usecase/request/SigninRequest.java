package com.kajisaab.ecommerce.feature.auth.usecase.request;

import com.kajisaab.ecommerce.core.usecase.UsecaseRequest;
import com.kajisaab.ecommerce.feature.auth.dto.SigninRequestParamDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninRequest implements UsecaseRequest {

    @NotNull
    @NotBlank(message = "Email or Username is required")
    private String emailOrUsername;

    @NotNull
    @NotBlank(message = "Password is required")
    private String password;

    public SigninRequest(SigninRequestParamDto request) {
        this.emailOrUsername = request.email();
        this.password = request.password();
    }
}
