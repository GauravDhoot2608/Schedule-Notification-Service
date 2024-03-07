package com.notificationservice.ScheduledNotificationService.Repositories;

import com.notificationservice.ScheduledNotificationService.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
