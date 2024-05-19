package com.kajisaab.ecommerce.core.emailconfig.impl;

import com.kajisaab.ecommerce.core.emailconfig.EmailService;
import com.kajisaab.ecommerce.core.emailconfig.emailtemplate.EmailTemplates;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Objects;
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String EMAIL_TEMPLATE = "otpcodeemailtemplate";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final EmailTemplates emailTemplates;

    @Override
    @Async
    public void sendSimpleEmail(String to) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("OTP Verification");
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(to);
            mailMessage.setText("This is the testing");
            emailSender.send(mailMessage);
            System.out.println("Successfully sent the simple email ");
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendMimeMessageWithAttachments(String name, String to, String token) {
        try {
            MimeMessage mailMessage = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject("OTP Verification");
            helper.setFrom(fromEmail, "Ecommerce");
            helper.setTo(to);
            helper.setText(emailTemplates.generateVerificationUrlEmail(name, host, token));
//            Add attachments
//            FileSystemResource {fileName} = new FileSystemResource((new File(System.getProperty('location.home') + "/Downloads/images/fileName.jpg")));
//            helper.addInline(getContentId(fileName.getFilename()), fileName);
            emailSender.send(mailMessage);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendHtmlEmail(Context context, String to, String emailTemplate){
        sendHtmlEmail(context, to, emailTemplate, "OTP Verification");
    }

    @Override
    @Async
    public void sendHtmlEmail(Context context, String to, String emailTemplate, String subject ) {
        String template = Objects.requireNonNullElse(emailTemplate, "otpCodeEmailTemplate");
        try {
            String text = templateEngine.process(template, context);
            MimeMessage mailMessage = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(subject);
            helper.setFrom(fromEmail, "Ecommerce");
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(mailMessage);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getContentId(String filename){
        return "<" + filename + ">";
    }

    private MimeMessage getMimeMessage(){
        return emailSender.createMimeMessage();
    }
}
