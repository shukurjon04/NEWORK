package online.job.onlinejobnew.Aop;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import online.job.onlinejobnew.Repository.users.AdminRepository;
import online.job.onlinejobnew.Repository.users.ClientRepository;
import online.job.onlinejobnew.Repository.users.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !clientRepository.existsByUsername(value) && adminRepository.existsByUsername(value) && employeeRepository.existsByUsername(value);
    }
}
