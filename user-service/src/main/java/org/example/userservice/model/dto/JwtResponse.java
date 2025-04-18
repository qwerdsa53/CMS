package org.example.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private Long userId;
    private String username;
    private List<String> avatarUrl;
    private String accessToken;
}
