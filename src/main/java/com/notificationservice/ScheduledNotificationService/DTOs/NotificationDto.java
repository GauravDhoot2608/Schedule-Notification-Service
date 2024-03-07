package com.notificationservice.ScheduledNotificationService.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private String content;
    private LocalDateTime scheduledTime;
    private boolean recurring;
    private String frequency; // daily, weekly, monthly
    private String deliveryMethod; // email or push notification
}
