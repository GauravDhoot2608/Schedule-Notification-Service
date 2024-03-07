package com.notificationservice.ScheduledNotificationService.Controllers;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.notificationservice.ScheduledNotificationService.DTOs.NotificationDto;
import com.notificationservice.ScheduledNotificationService.Entities.Notification;
import com.notificationservice.ScheduledNotificationService.Services.EmailNotificationService;
import com.notificationservice.ScheduledNotificationService.Services.NotificationService;
import com.notificationservice.ScheduledNotificationService.Services.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Scheduled(fixedRate = 60000) // Check every minute
    public void scheduleNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        LocalDateTime now = LocalDateTime.now();
        for (Notification notification : notifications) {
            if (notification.getScheduledTime().isBefore(now)) {
                if ("email".equals(notification.getDeliveryMethod())) {
                    try {
                        emailNotificationService.sendEmailNotification(notification.getUser().getEmail(), notification.getContent());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                } else if ("push".equals(notification.getDeliveryMethod())) {
                    // Send push notification
                    try {
                        pushNotificationService.sendPushNotification("Notification" , notification.getContent());
                    }catch (FirebaseMessagingException e){
                        e.printStackTrace();
                    }
                }
                if (notification.isRecurring()) {
                    // Reschedule recurring notification
                    rescheduleRecurringNotification(notification);
                } else {
                    // Delete one-time notification
                    notificationService.deleteNotificationById(notification.getNotificationId());
                }
            }
        }
    }

    private void rescheduleRecurringNotification(Notification notification) {
        LocalDateTime nextScheduledTime;
        switch (notification.getFrequency()) {
            case "daily":
                nextScheduledTime = notification.getScheduledTime().plusDays(1);
                break;
            case "weekly":
                nextScheduledTime = notification.getScheduledTime().plusWeeks(1);
                break;
            case "monthly":
                nextScheduledTime = notification.getScheduledTime().plusMonths(1);
                break;
            default:
                // Default to daily if frequency is not recognized
                nextScheduledTime = notification.getScheduledTime().plusDays(1);
                break;
        }
        notification.setScheduledTime(nextScheduledTime);

        // Update the notification in the database
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setContent(notification.getContent());
        notificationDto.setFrequency(notification.getFrequency());
        notificationDto.setRecurring(notification.isRecurring());
        notificationDto.setScheduledTime(notification.getScheduledTime());
        notificationDto.setDeliveryMethod(notification.getDeliveryMethod());

        notificationService.updateNotification(notificationDto , notification.getNotificationId());
    }

}
