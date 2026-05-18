package com.example.iotrfidbe.service.auth;
import com.example.iotrfidbe.dto.request.LoginRequest;
import com.example.iotrfidbe.dto.request.RegisterRequest;
import com.example.iotrfidbe.dto.response.LoginResponse;
import com.example.iotrfidbe.entity.Users;
import com.example.iotrfidbe.exception.UserNotFoundException;
import com.example.iotrfidbe.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    static final String serverRep = ">__server: " ;
    @Autowired
    public AuthService(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        this.usersRepository =usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Object login(LoginRequest request) {
        var user = usersRepository.findByUsername(request.getUserName()).orElseThrow(
                () -> new UserNotFoundException(serverRep + "user not found.")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException(serverRep + "wrong password");
        }
        return new LoginResponse(
                serverRep + "login Success",
                user.getUsername(),
                user.getEmail(),
                user.getId(),
                "");
    }
    public Object register(RegisterRequest request) {
        // hàm isPresent() là hàm check 1 Optional có tồn tại hay không
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            return serverRep + "exists_email";
        }
        if (usersRepository.findByUsername(request.getUsername()).isPresent()) {
            return serverRep + "exists_username";
        }

        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Tạo user mới
        Users newUser = new Users();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);

        // Lưu vào database
        usersRepository.save(newUser);

        return serverRep + "register successfull";
    }
}
