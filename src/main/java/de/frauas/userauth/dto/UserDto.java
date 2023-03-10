package de.frauas.userauth.dto;

import de.frauas.userauth.enums.RoleType;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private List<RoleType> roles;
}
