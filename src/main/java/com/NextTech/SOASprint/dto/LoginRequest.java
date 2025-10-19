package com.NextTech.SOASprint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor; 
import lombok.Getter;
import lombok.NoArgsConstructor; 
import lombok.Setter;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @NotBlank 
    @Email
    private String email; 
    
    @NotBlank
    private String password;
}