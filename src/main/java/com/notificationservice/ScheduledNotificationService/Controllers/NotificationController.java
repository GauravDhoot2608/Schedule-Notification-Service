package com.notificationservice.ScheduledNotificationService.Controllers;

import com.notificationservice.ScheduledNotificationService.DTOs.NotificationDto;
import com.notificationservice.ScheduledNotificationService.DTOs.RescheduleDto;
import com.notificationservice.ScheduledNotificationService.Entities.Notification;
import com.notificationservice.ScheduledNotificationService.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/schedule/{userId}")
    public ResponseEntity scheduleNotification(@RequestBody NotificationDto notificationDto , @PathVariable Long userId){
        try {
            Notification notification = notificationService.scheduleNotification(notificationDto , userId);
            return new ResponseEntity(notification , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity getAllNotifications(){
        List<Notification> notifications = notificationService.getAllNotifications();
        return new ResponseEntity(notifications , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNotificationById(@PathVariable Long id){
        try {
            Notification notification = notificationService.getNotificationById(id);
            return new ResponseEntity(notification , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity deleteNotificationById(@PathVariable Long notificationId){
        notificationService.deleteNotificationById(notificationId);
        return new ResponseEntity("Notification deleted successfully" , HttpStatus.OK);
    }

    @PutMapping("/update/{notificationId}")
    public ResponseEntity updateNotification(@RequestBody NotificationDto notificationDto , @PathVariable Long notificationId){

        try {
            Notification notification = notificationService.updateNotification(notificationDto , notificationId);
            return new ResponseEntity(notification , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reschedule/{notificationId}")
    public ResponseEntity rescheduleNotification(@RequestBody RescheduleDto rescheduleDto , @PathVariable Long notificationId){

        try {
            Notification notification = notificationService.rescheduleNotification(rescheduleDto,notificationId);
            return new ResponseEntity(notification , HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
