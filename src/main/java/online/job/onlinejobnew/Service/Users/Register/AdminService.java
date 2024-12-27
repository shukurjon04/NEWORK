package online.job.onlinejobnew.Service.Users.Register;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import online.job.onlinejobnew.Dto.*;
import online.job.onlinejobnew.Dto.Register.LoginDto;
import online.job.onlinejobnew.Dto.Register.UserDto;
import online.job.onlinejobnew.Dto.Register.VerifyDto;
import online.job.onlinejobnew.Entity.RoleEntiy.Role;
import online.job.onlinejobnew.Entity.RoleEntiy.RoleName;
import online.job.onlinejobnew.Entity.Users.Admin;
import online.job.onlinejobnew.Entity.Users.Client;
import online.job.onlinejobnew.Entity.Users.Employee;
import online.job.onlinejobnew.Repository.Role.RoleRepository;
import online.job.onlinejobnew.Repository.users.AdminRepository;
import online.job.onlinejobnew.Repository.users.ClientRepository;
import online.job.onlinejobnew.Repository.users.EmployeeRepository;
import online.job.onlinejobnew.Service.Users.Register.AllUserServiceInterfaces.AdminServiceInterface;
import online.job.onlinejobnew.Service.Users.Register.CloneUserService.UserService;
import online.job.onlinejobnew.Service.Users.Register.VerificationMethods.VerifyAllUsers;
import online.job.onlinejobnew.Token.JwtProvider;
import online.job.onlinejobnew.Utils.GenerateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminService implements AdminServiceInterface {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final VerifyAllUsers verifyAllUsers;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, UserService userService, RoleRepository roleRepository, VerifyAllUsers verifyAllUsers, AuthenticationManager authenticationManager, JwtProvider jwtProvider, EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.verifyAllUsers = verifyAllUsers;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public ApiResponse register(UserDto userDto) throws ClassNotFoundException, ServerErrorException {
        Role role = roleRepository.findByRoleName(RoleName.Admin).orElseThrow(() -> new ClassNotFoundException("role not found"));
        ApiResponse user = userService.registerUser(userDto);
        if (user.isSuccess()) {
            Admin admin = new Admin();
            admin.setUsername(userDto.getUsername());
            admin.setEmail(userDto.getEmail());
            admin.setRole(role);
            admin.setFirstName(userDto.getFirstName());
            admin.setLastName(userDto.getLastName());
            admin.setPassword(passwordEncoder.encode(userDto.getPassword()));
            admin.setPhoneNumber(userDto.getPhone());
            admin.setVerificationCode(user.getData().toString());
            Admin save = adminRepository.save(admin);
            return new ApiResponse(HttpStatus.CREATED.value(), "Admin created successfully", true, save);
        }
        throw new ServerErrorException("server error exception", new RuntimeException("server error exception"));
    }

    @Override
    public ApiResponse verify(VerifyDto verifyDto) {
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(verifyDto.getEmail());
        if (optionalAdmin.isPresent()) {
            return verifyAllUsers.verifyAdmin(optionalAdmin.get(), verifyDto.getCode(), adminRepository);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "Admin not found", false, null);
    }

    @Override
    public ApiResponse resendCode(String email) throws Exception {
        Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> new ClassNotFoundException("email not found"));
        String emailCode = GenerateCode.Code(6);
        admin.setVerificationCode(emailCode);
        if (userService.SendEmail(email, emailCode)) {
            adminRepository.save(admin);
            return new ApiResponse(HttpStatus.OK.value(), "code was sent", true, null);
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "email not found", false, null);
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginDto.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails, loginDto.getPassword(), userDetails.getAuthorities())
            );
            if (authentication != null && authentication.isAuthenticated()) {
                Admin admin = (Admin) authentication.getPrincipal();
                String token = jwtProvider.generateToken(admin.getUsername(), admin.getEmail(), admin.getRole());
                return new ApiResponse(HttpStatus.OK.value(), "Admin logged in successfully", true, token);
            }
            return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password", false, null);
        } catch (Exception e) {
            throw new UsernameNotFoundException("username or email not found");
        }
    }

    @Override
    public ApiResponse logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            return new ApiResponse(HttpStatus.OK.value(), "Successfully logged out", true, null);
        } catch (Exception e) {
            return new ApiResponse(HttpStatus.FAILED_DEPENDENCY.value(), "Logout failed: " + e.getMessage(), false, null);
        }
    }

    @Override
    public ApiResponse changePassword(ChangePassw passw) {
        Optional<Admin> byId = adminRepository.findById(passw.getId());
        if (byId.isPresent()) {
            Admin admin = byId.get();
            if (passwordEncoder.matches(passw.getOldPassw(), admin.getPassword())) {
                admin.setPassword(passwordEncoder.encode(passw.getNewPassw()));
                Admin save = adminRepository.save(admin);
                return new ApiResponse(HttpStatus.OK.value(), "Password changed successfully", true, save);
            }
            return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Incorrect password", false, null);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "Admin not found", false, null);
    }

    @Override
    public ApiResponse getALLEmployees() {
        List<Employee> all = employeeRepository.findAll();
        return new ApiResponse(HttpStatus.OK.value(), "seccess", true, all);
    }

    @Override
    public ApiResponse getAllClients() {
        List<Client> all = clientRepository.findAll();
        return new ApiResponse(HttpStatus.OK.value(), "seccess",true,all);
    }

    @Override
    public ApiResponse getAllAdmins() {
        List<Admin> all = adminRepository.findAll();
        return new ApiResponse(HttpStatus.OK.value(), "seccess",true,all);
    }

    @Override
    public ApiResponse deleteEmployee(UUID id)  {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employeeRepository.delete(employee);
            return new ApiResponse(HttpStatus.OK.value(), "Employee deleted successfully", true, null);
        }
        return new ApiResponse(HttpStatus.NO_CONTENT.value(), "User not found", false, null);
    }

    @Override
    public ApiResponse deleteClient(UUID id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            clientRepository.delete(client);
            return new ApiResponse(HttpStatus.OK.value(), "Client deleted successfully", true, null);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found", false, null);
    }

    @Override
    public ApiResponse getEmployee(UUID id)  {
        Optional<Employee> byId = employeeRepository.findById(id);
        if (byId.isPresent()) {
            Employee employee = byId.get();
            return new ApiResponse(HttpStatus.OK.value(), "Employee found successfully", true, employee);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found", false, null);
    }

    @Override
    public ApiResponse getClient(UUID id)  {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            return new ApiResponse(HttpStatus.OK.value(), "Client found successfully", true, client);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "User not found", false, null);
    }
}
