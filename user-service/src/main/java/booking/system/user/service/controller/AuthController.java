package booking.system.user.service.controller;


import booking.system.user.service.payload.dto.LoginDTO;
import booking.system.user.service.payload.dto.SignupDTO;
import booking.system.user.service.payload.response.AuthResponse;
import booking.system.user.service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(
            @RequestBody SignupDTO req
            ) throws Exception {
        AuthResponse res = authService.signup(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginDTO req
    ) throws Exception {
        AuthResponse res = authService.login(req.getEmail(),req.getPassword());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> getAccessToken(
            @PathVariable String refreshToken
    ) throws Exception {
        AuthResponse res = authService.getAccessTokenFromRefreshToken(refreshToken);
        return ResponseEntity.ok(res);
    }


}
