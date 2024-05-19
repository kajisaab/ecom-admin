package com.kajisaab.ecommerce.core.emailconfig;

import org.thymeleaf.context.Context;

public interface EmailService {
    void sendSimpleEmail(String to);

    void sendMimeMessageWithAttachments(String name, String to, String token);

    void sendHtmlEmail(Context context, String to, String emailTemplate);

    void sendHtmlEmail(Context context, String to, String emailTemplate, String subject);
}
