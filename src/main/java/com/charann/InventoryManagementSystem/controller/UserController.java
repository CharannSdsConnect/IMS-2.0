package com.charann.InventoryManagementSystem.controller;

import com.charann.InventoryManagementSystem.repository.UserRepository;
import com.charann.InventoryManagementSystem.service.UserService;
import com.charann.InventoryManagementSystem.dto.LoginDto;
import com.charann.InventoryManagementSystem.dto.RegisterDto;
import com.charann.InventoryManagementSystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    return new ResponseEntity<>(userService.register(registerDto), HttpStatus.OK);
  }

  @PutMapping("/verify-account")
  public ResponseEntity<String> verifyAccount(@RequestParam String email,
      @RequestParam String otp) {
    return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
  }

  @PutMapping("/regenerate-otp")
  public ResponseEntity<String> regenerateOtp(@RequestParam String email) {
    return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
  }

  @PutMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
    return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
  }

  @PutMapping("/logout/{id}")
  public String logout(@PathVariable String id) {
    User user = userRepository.findByEmail(id).orElseThrow(
            () -> new RuntimeException("User not found with this email: " + id));
    user.setLoggedInStatus(false);
    userRepository.save(user);
    return "Logged out";
  }

}
