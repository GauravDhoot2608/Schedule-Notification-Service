package com.notificationservice.ScheduledNotificationService.DTOs;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RescheduleDto {

    private LocalDateTime newScheduleTime;
}
