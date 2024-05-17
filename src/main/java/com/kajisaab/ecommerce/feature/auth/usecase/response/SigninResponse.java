package com.kajisaab.ecommerce.feature.auth.usecase.response;

import com.kajisaab.ecommerce.core.usecase.UsecaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SigninResponse implements UsecaseResponse {

    public String token;
    public String refreshToken;

}
