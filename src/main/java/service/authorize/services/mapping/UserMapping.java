package service.authorize.services.mapping;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import service.authorize.dto.RegisterDto;
import service.authorize.entities.User;
import service.authorize.repositories.UserRepository;

@Service
public class UserMapping {
    private final PasswordEncoder passwordEncoder;

    public UserMapping(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(RegisterDto userInfo){

        User user = new User();

        user.setFirstName(userInfo.getFirstName());
        user.setSecondName(userInfo.getSecondName());
        user.setThirdName(userInfo.getThirdName());
        user.setEmail(userInfo.getEmail());
        user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        user.setPhoneNumber(userInfo.getPhoneNumber());
        user.setSeriesPassport(userInfo.getPassport().getSeriesPassport());
        user.setNumberPassport(userInfo.getPassport().getNumberPassport());
        user.setCity(userInfo.getCity());
        user.setCountry(userInfo.getCountry());
        user.setIssuedDate(userInfo.getPassport().getIssuedDate());
        user.setExpirationDate(userInfo.getPassport().getExpirationDate());
        user.setIssuedBy(userInfo.getPassport().getIssuedBy());
        user.setSex(userInfo.getPassport().getSex());
        user.setDepartmentCode(userInfo.getPassport().getDepartmentCode());
        user.setBirthDate(userInfo.getPassport().getBirthDate());
        user.setCompanyName(userInfo.getEmployeeInfo().getCompanyName());
        user.setSalary(userInfo.getEmployeeInfo().getSalary());
        user.setStartDate(userInfo.getEmployeeInfo().getStartDate());
        user.setInn(userInfo.getEmployeeInfo().getInn());

        return user;
    }
}
