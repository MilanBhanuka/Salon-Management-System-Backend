package booking.system.user.service.payload.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
