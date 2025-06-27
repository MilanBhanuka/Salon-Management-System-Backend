package booking.system.payload.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String FullName;
    private String email;
}
