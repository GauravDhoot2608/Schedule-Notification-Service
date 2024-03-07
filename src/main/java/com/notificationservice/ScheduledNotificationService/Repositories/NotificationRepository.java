package com.notificationservice.ScheduledNotificationService.Repositories;

import com.notificationservice.ScheduledNotificationService.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
}
