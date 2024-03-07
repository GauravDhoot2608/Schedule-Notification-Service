package com.notificationservice.ScheduledNotificationService.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SENDER_MAIL = "NotificationService@gmail.com";
    private static final String MAIL_SUBJECT = "Notification - Email";
    public void sendEmailNotification(String to , String body) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_MAIL);
        message.setTo(to);
        message.setSubject(MAIL_SUBJECT);
        message.setText(body);
        try {
            javaMailSender.send(message);
        }catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
