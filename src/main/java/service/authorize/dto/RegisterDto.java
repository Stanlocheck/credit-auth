package service.authorize.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterDto {
    private String firstName;
    private String secondName;
    private String thirdName;
    private String email;
    private String password;
    private String phoneNumber;
    private String city;
    private String country;

    private Passport passport;
    private EmployeeInfo employeeInfo;

    @Getter
    @Setter
    public static class Passport {
        private Long seriesPassport;
        private Long numberPassport;
        private String issuedBy;
        private LocalDate expirationDate;
        private LocalDate issuedDate;
        private Boolean sex;
        private LocalDate birthDate;
        private String departmentCode;
    }

    @Getter
    @Setter
    public static class EmployeeInfo {
        private String companyName;
        private Integer salary;
        private LocalDate startDate;
        private String inn;
    }
}
