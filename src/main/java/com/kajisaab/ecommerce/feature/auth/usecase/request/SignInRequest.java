package com.kajisaab.ecommerce.feature.auth.usecase.request;

import com.kajisaab.ecommerce.core.usecase.UsecaseRequest;
import com.kajisaab.ecommerce.feature.auth.dto.SignInRequestParamDto;
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
public class SignInRequest implements UsecaseRequest {

    @NotNull
    @NotBlank(message = "Email or Username is required")
    private String userId;

    @NotNull
    @NotBlank(message = "Password is required")
    private String password;

    public SignInRequest(SignInRequestParamDto request) {
        this.userId = request.userId();
        this.password = request.password();
    }
}
