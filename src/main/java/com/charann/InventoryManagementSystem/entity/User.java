package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  private String password;

  @Column
  private boolean active;

  @Column
  private String otp;

  @Column
  private LocalDateTime otpGeneratedTime;

  @Column
  private boolean loggedInStatus;

  @Column
  private String role;

}
