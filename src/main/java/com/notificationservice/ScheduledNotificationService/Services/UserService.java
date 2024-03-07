package com.notificationservice.ScheduledNotificationService.Services;

import com.notificationservice.ScheduledNotificationService.DTOs.UserDto;
import com.notificationservice.ScheduledNotificationService.Entities.User;
import com.notificationservice.ScheduledNotificationService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return  userRepository.save(user);
    }

    public User getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NullPointerException("User not found Exception"));
        return user;
    }
}
