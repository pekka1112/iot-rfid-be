package com.example.iotrfidbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String message;
    private String username;
    private String email;
    private int id;
    private String token; // nếu bạn có dùng JWT

}