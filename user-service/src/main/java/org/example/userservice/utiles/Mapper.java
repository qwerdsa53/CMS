package org.example.userservice.utiles;

import org.example.userservice.model.User;
import org.example.userservice.model.dto.LiteUserDto;
import org.example.userservice.model.dto.UserDto;

public interface Mapper {

    UserDto convertToDto(User user, Boolean isOnline);

    LiteUserDto convertToLiteDto(User user, boolean isOnline);
}
