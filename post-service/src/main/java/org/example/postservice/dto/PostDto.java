package org.example.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long userId;
    private String title;
    private String text;
    private List<String> tags;
    private LocalDateTime createdAt;
}

