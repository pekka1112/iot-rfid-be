package com.example.iotrfidbe.controller.auth;
import com.example.iotrfidbe.dto.request.LoginRequest;
import com.example.iotrfidbe.dto.request.RegisterRequest;
import com.example.iotrfidbe.repository.UsersRepository;
import com.example.iotrfidbe.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        var response = authService.login(request); // var sẽ có từ java10. -> java tự suy luận datatype của biến bên phải
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        var result = authService.register(request);
        if ("exists_email".equals(result) || "exists_username".equals(result)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
