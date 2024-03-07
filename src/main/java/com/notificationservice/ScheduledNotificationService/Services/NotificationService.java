package com.notificationservice.ScheduledNotificationService.Services;

import com.notificationservice.ScheduledNotificationService.DTOs.NotificationDto;
import com.notificationservice.ScheduledNotificationService.DTOs.RescheduleDto;
import com.notificationservice.ScheduledNotificationService.Entities.Notification;
import com.notificationservice.ScheduledNotificationService.Entities.User;
import com.notificationservice.ScheduledNotificationService.Repositories.NotificationRepository;
import com.notificationservice.ScheduledNotificationService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public Notification scheduleNotification(NotificationDto notificationDto , Long userId) {
        // Validate input data
        if (notificationDto == null || notificationDto.getContent() == null || notificationDto.getScheduledTime() == null) {
            throw new IllegalArgumentException("Notification data is invalid");
        }
        // Ensure scheduled time is in the future
        LocalDateTime now = LocalDateTime.now();
        if (notificationDto.getScheduledTime().isBefore(now)) {
            throw new IllegalArgumentException("Scheduled time must be in the future");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("User not found Exception"));

        Notification notification = new Notification();
        notification.setContent(notificationDto.getContent());
        notification.setScheduledTime(notificationDto.getScheduledTime());
        notification.setRecurring(notificationDto.isRecurring());
        notification.setFrequency(notificationDto.getFrequency());
        notification.setDeliveryMethod(notificationDto.getDeliveryMethod());
        notification.setUser(user);

        notification = notificationRepository.save(notification);
        return notification;
    }


    public List<Notification> getAllNotifications() {

        List<Notification> notificationList = notificationRepository.findAll();
        return notificationList;
    }


    public Notification getNotificationById(Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NullPointerException("Notification with given id" + notificationId + " is Not Valid"));
        return notification;
    }


    public void deleteNotificationById(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }


    public Notification updateNotification(NotificationDto updatedNotificationDto , Long notificationId ) {

        Notification existingNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NullPointerException("Notification with given id" + notificationId + " is Not Valid"));

        if (updatedNotificationDto == null || updatedNotificationDto.getContent() == null || updatedNotificationDto.getScheduledTime() == null) {
            throw new IllegalArgumentException("Notification data is invalid");
        }

        // update the attributes
        existingNotification.setContent(updatedNotificationDto.getContent());
        existingNotification.setFrequency(updatedNotificationDto.getFrequency());
        existingNotification.setScheduledTime(updatedNotificationDto.getScheduledTime());
        existingNotification.setRecurring(updatedNotificationDto.isRecurring());
        existingNotification.setDeliveryMethod(updatedNotificationDto.getDeliveryMethod());

        // Save the updated notification in the database
        return notificationRepository.save(existingNotification);
    }


    public Notification rescheduleNotification(RescheduleDto rescheduleDto , Long notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NullPointerException("Notification with given id" + notificationId + " is Not Valid"));

        // Ensure the new scheduled time is in the future
        LocalDateTime now = LocalDateTime.now();
        if (rescheduleDto.getNewScheduleTime().isBefore(now)) {
            throw new IllegalArgumentException("New scheduled time must be in the future");
        }

        // Update the scheduled time of the notification
        notification.setScheduledTime(rescheduleDto.getNewScheduleTime());

        // Save the updated notification in the database
        return notificationRepository.save(notification);
    }
}
