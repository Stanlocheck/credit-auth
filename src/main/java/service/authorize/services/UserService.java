package service.authorize.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import service.authorize.dto.RegisterDto;
import service.authorize.entities.User;
import service.authorize.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterDto request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setThirdName(request.getThirdName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setSeriesPassport(request.getPassport().getSeriesPassport());
        user.setNumberPassport(request.getPassport().getNumberPassport());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setIssuedDate(request.getPassport().getIssuedDate());
        user.setExpirationDate(request.getPassport().getExpirationDate());
        user.setIssuedBy(request.getPassport().getIssuedBy());
        user.setSex(request.getPassport().getSex());
        user.setDepartmentCode(request.getPassport().getDepartmentCode());
        user.setBirthDate(request.getPassport().getBirthDate());
        user.setCompanyName(request.getEmployeeInfo().getCompanyName());
        user.setSalary(request.getEmployeeInfo().getSalary());
        user.setStartDate(request.getEmployeeInfo().getStartDate());
        user.setInn(request.getEmployeeInfo().getInn());
        userRepository.save(user);
        return user;
    }
}
