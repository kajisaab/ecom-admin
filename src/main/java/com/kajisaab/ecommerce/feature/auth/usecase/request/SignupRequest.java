package com.kajisaab.ecommerce.feature.auth.usecase.request;

import com.kajisaab.ecommerce.core.usecase.UsecaseRequest;
import com.kajisaab.ecommerce.feature.auth.dto.SignupRequestBodyDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest implements UsecaseRequest {
    @NotNull(message = "First Name cannot be null")
    @NotBlank(message = "First Name cannot be empty")
    private String firstName;

    @NotNull(message="Last Name cannot be null")
    @NotBlank(message = "Last Name cannot be empty")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 10, message = "Password must be in between 8 to 10")
    private String password;

    @NotNull(message = "Username cannot be null")
    private String userName;

    @NotNull(message = "Phone Number cannot be null")
    @NotBlank(message = "Phone Number cannot be empty")
    @Size(min = 10, max = 10, message = "Phone Number must be of 10 digit")
    private String phoneNumber;

    public SignupRequest(SignupRequestBodyDto request){
        this.firstName = request.firstName();
        this.lastName = request.lastName();
        this.email = request.email();
        this.password = request.password();
        this.userName = request.userName();
        this.phoneNumber = request.phoneNumber();
    }
}
