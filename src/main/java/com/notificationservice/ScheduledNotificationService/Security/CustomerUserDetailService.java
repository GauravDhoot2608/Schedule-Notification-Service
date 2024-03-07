package com.notificationservice.ScheduledNotificationService.Security;

import com.notificationservice.ScheduledNotificationService.Entities.User;
import com.notificationservice.ScheduledNotificationService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomerUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = this.userRepo.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User email not found"));
        return user;
    }
}
