package com.charann.InventoryManagementSystem.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDto {

  private String email;
  private String password;
}
