package online.job.onlinejobnew.Service.Users.Register.CloneUserService;


import jakarta.mail.internet.MimeMessage;
import online.job.onlinejobnew.Dto.ApiResponse;
import online.job.onlinejobnew.Dto.Register.UserDto;
import online.job.onlinejobnew.Entity.Users.Admin;
import online.job.onlinejobnew.Entity.Users.Client;
import online.job.onlinejobnew.Entity.Users.Employee;
import online.job.onlinejobnew.Repository.users.AdminRepository;
import online.job.onlinejobnew.Repository.users.ClientRepository;
import online.job.onlinejobnew.Repository.users.EmployeeRepository;
import online.job.onlinejobnew.Service.Users.Register.AllUserServiceInterfaces.UserRegisterInterface;
import online.job.onlinejobnew.Utils.GenerateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService, UserRegisterInterface {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public UserService(AdminRepository adminRepository, EmployeeRepository employeeRepository, ClientRepository clientRepository, JavaMailSender mailSender) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return admin.get();
        }
        Optional<Client> client = clientRepository.findByUsername(username);
        if (client.isPresent()) {
            return client.get();
        }
        Optional<Employee> employee = employeeRepository.findByUsername(username);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    public ApiResponse registerUser(UserDto userDto) {
        return existUserField(userDto.getUsername(), userDto.getEmail());
    }

    private ApiResponse existUserField(String username, String email) {
        if (clientRepository.existsByUsername(username) && adminRepository.existsByUsername(username) && employeeRepository.existsByUsername(username)) {
            return new ApiResponse(HttpStatus.FORBIDDEN.value(), "username already exist", false, null);
        }
        if (adminRepository.existsByEmail(email) && employeeRepository.existsByEmail(email) && clientRepository.existsByEmail(email)) {
            return new ApiResponse(HttpStatus.FORBIDDEN.value(), "email already exist", false, null);
        }
        String emailCode = GenerateCode.Code(6);
        if (SendEmail(email, emailCode)) {
            return new ApiResponse(HttpStatus.FORBIDDEN.value(), "email was sent", true, emailCode);
        }
        return new ApiResponse(HttpStatus.OK.value(), "failed", false, null);
    }


    @Value("${spring.mail.username}")
    private String EmailUsername;


    public boolean SendEmail(String to, String message) {
        try {
            String htmlMessage = """
                        <div style="font-family: Arial, sans-serif; color: #333;">
                            <h2 style="color: #2e86de;">Xush kelibsiz!</h2>
                            <p>Hurmatli foydalanuvchi, quyidagi ma'lumotlarni tasdiqlang:</p>
                            <p style="font-size: 14px; padding: 10px 20px; display: inline-block; background-color: blue; color: white;">{message}</p>
                            <p>Hurmat bilan, <br><strong>Shukurjon tomonidan tasdiqlandi</strong></p>
                            <footer style="font-size: 12px; color: #777;">
                                Agar bu xabarni sizga noqulaylik tug'dirayotgan bo'lsa, iltimos biz bilan bog'laning.
                            </footer>
                        </div>
                    """.replace("{message}", message);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(EmailUsername);
            helper.setText(htmlMessage, true);
            helper.setSubject("verified by Shukurjon");
            helper.setTo(to);
            mailSender.send(mimeMessage);
            return true;
        } catch (Exception e) {
            throw new ServerErrorException("don't send message server error", e.getCause());
        }
    }

}
