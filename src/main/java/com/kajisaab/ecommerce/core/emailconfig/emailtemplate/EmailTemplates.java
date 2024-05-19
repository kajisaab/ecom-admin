package com.kajisaab.ecommerce.core.emailconfig.emailtemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTemplates {

    public String generateVerificationUrlEmail(String name, String host, String token) {
        return "Hello " + name + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n"
                + getVerificationUrl(host, token) + "\n\nThe Support Team";
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/api/users?token=" + token;
    }

}
