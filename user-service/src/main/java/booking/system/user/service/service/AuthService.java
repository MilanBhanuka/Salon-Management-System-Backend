package booking.system.user.service.service;

import booking.system.user.service.payload.dto.SignupDTO;
import booking.system.user.service.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String username, String password) throws Exception;
    AuthResponse signup(SignupDTO req) throws Exception;
    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
