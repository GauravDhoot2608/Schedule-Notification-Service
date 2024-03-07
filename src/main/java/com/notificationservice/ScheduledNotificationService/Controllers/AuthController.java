package com.notificationservice.ScheduledNotificationService.Controllers;

import com.notificationservice.ScheduledNotificationService.DTOs.JWTAuthRequest;
import com.notificationservice.ScheduledNotificationService.DTOs.JWTAuthResponse;
import com.notificationservice.ScheduledNotificationService.DTOs.UserDto;
import com.notificationservice.ScheduledNotificationService.Entities.User;
import com.notificationservice.ScheduledNotificationService.Security.JWTTokenHelper;
import com.notificationservice.ScheduledNotificationService.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    // register new user api
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDto userDto){
        User registeredUser = this.userService.addUser(userDto);
        return new ResponseEntity(registeredUser,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity createToken(@RequestBody JWTAuthRequest request) throws Exception {
        try{
            this.authenticate(request.getUsername(),request.getPassword());
        }catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setToken(token);

        return new ResponseEntity<>(jwtAuthResponse , HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);

        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }catch (BadCredentialsException e){
            throw new Exception("Invalid Username and Password");
        }
    }



}

