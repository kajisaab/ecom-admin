package com.kajisaab.ecommerce.feature.auth.dto;

public record OtpVerificationRequestBodyDto(String userId, String otp) {
}
