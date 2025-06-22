package booking.system.user.service.payload.dto;

import booking.system.user.service.domain.UserRole;
import lombok.Data;

@Data
public class SignupDTO {
    private String fullName;
    private String email;
    private String password;
    private String username;
    private UserRole role;
}
