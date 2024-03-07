package com.notificationservice.ScheduledNotificationService.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;
    private String content;
    private LocalDateTime scheduledTime;
    private boolean recurring;
    private String frequency; // daily, weekly, monthly
    private String deliveryMethod; // email or push notification

    @JoinColumn
    @ManyToOne
    private User user;
}
