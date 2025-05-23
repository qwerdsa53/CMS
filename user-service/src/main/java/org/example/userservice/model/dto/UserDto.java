package org.example.userservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.userservice.model.dto.validation.OnCreate;
import org.example.userservice.model.dto.validation.OnUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    @NotNull(message = "Username must not be null.")
    @Size(max = 255, message = "Username length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @NotNull(message = "Email must not be null.")
    @Size(max = 255, message = "Email length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String email;

    private boolean enabled;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthday must not be null.")
    @Size(max = 255, message = "Birthday length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private LocalDate birthday;

    @Size(max = 1500, message = "Description length must be smaller than 1500 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private List<String> profilePictures;

    @NotNull(message = "Password must not be null", groups = {OnCreate.class})
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rawPassword;

    private Boolean isOnline;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSeen;
}
