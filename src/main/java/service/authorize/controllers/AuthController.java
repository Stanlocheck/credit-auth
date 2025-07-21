package service.authorize.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.authorize.components.JwtUtil;
import service.authorize.dto.LoginDto;
import service.authorize.dto.RegisterDto;
import service.authorize.dto.ResponseWrapper;
import service.authorize.entities.User;
import service.authorize.repositories.UserRepository;
import service.authorize.services.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody RegisterDto registerDto) {
        try {
            User user = userService.registerUser(registerDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseWrapper.success(Map.of("user", user.getEmail())));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseWrapper.error(null));
        }
    }

    @PostMapping("/signin")
    ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(),
                            loginDto.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok()
                    .body(ResponseWrapper.success(Map.of("token", token)));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseWrapper.unauthorized());
        }
    }
}
