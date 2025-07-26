package service.authorize.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import service.authorize.dto.RegisterDto;
import service.authorize.entities.User;
import service.authorize.repositories.UserRepository;
import service.authorize.services.mapping.UserMapping;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;

    public UserService(UserRepository userRepository, UserMapping userMapping) {
        this.userRepository = userRepository;
        this.userMapping = userMapping;
    }

    public User registerUser(RegisterDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = userMapping.toEntity(request);
        userRepository.save(user);
        return user;
    }
}
