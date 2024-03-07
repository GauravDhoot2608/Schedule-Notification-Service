package com.notificationservice.ScheduledNotificationService.DTOs;

import lombok.Data;

@Data
public class JWTAuthRequest {
    private String username;
    private String password;
}
