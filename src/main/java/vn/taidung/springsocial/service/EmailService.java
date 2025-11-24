package vn.taidung.springsocial.service;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml)
            throws MessagingException, MailException {
        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content, isHtml);
        this.javaMailSender.send(mimeMessage);
    }

    public void sendActivationEmail(String to, String username, String activationUrl)
            throws MessagingException, MailException {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("activationUrl", activationUrl);
        String content = this.templateEngine.process("activation", context);
        this.sendEmail(to, "Activate your account", content, false, true);
    }

}
