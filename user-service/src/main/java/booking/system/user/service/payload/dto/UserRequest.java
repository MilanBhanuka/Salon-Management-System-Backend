package booking.system.user.service.payload.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequest {
    private String username;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private List<Credential> credentials = new ArrayList<>();
}
