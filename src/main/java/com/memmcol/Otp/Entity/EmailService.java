package com.memmcol.Otp.Entity;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{

    private final JavaMailSender mailSender;
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Async
    @Override
    public void send(String to, String from, String message) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(to);
            helper.setSubject("no-reply");
            helper.setFrom(from);
            mailSender.send(mimeMessage);
            logger.info("message send successfully");
        }catch (MessagingException ex){
            logger.error("failed to send email");
            throw new IllegalStateException("failed to send email");
        }

    }
}
