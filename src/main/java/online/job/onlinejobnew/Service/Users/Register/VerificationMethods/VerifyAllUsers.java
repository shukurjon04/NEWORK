package online.job.onlinejobnew.Service.Users.Register.VerificationMethods;

import online.job.onlinejobnew.Dto.ApiResponse;
import online.job.onlinejobnew.Entity.Users.Admin;
import online.job.onlinejobnew.Entity.Users.Client;
import online.job.onlinejobnew.Entity.Users.Employee;
import online.job.onlinejobnew.Repository.users.AdminRepository;
import online.job.onlinejobnew.Repository.users.ClientRepository;
import online.job.onlinejobnew.Repository.users.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class VerifyAllUsers {

    public ApiResponse verifyAdmin(Admin admin , String code , AdminRepository adminRepository) {
        if (admin.getVerificationCode().equals(code)) {
            admin.setVerificationCode(null);
            admin.setEnabled(true);
            Admin save = adminRepository.save(admin);
            return new ApiResponse(HttpStatus.OK.value(), "verified", true, save);
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), " verification failed", false, null);
    }

    public ApiResponse verifyClient(Client client, String code , ClientRepository clientRepository) {
        if (client.getVerificationCode().equals(code)) {
            client.setVerificationCode(null);
            client.setEnabled(true);
            Client save = clientRepository.save(client);
            return new ApiResponse(HttpStatus.OK.value(), "verified", true, save);
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), " verification failed", false, null);
    }
    public ApiResponse verifyEmployee(Employee employee , String code , EmployeeRepository employeeRepository) {
        if (employee.getVerificationCode().equals(code)) {
            employee.setVerificationCode(null);
            employee.setEnabled(true);
            Employee save = employeeRepository.save(employee);
            return new ApiResponse(HttpStatus.OK.value(), "verified", true, save);
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), " verification failed", false, null);
    }
}
