package com.kajisaab.ecommerce.feature.auth.dto;

public record SignupRequestBodyDto(
        String email, String userName,
        String password, String firstName,
        String lastName, String phoneNumber) {}


