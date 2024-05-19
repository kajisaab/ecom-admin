package com.kajisaab.ecommerce.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOtpCodeDto {
    private int otpCode;
    private LocalDateTime expiryTime;
}
