package online.job.onlinejobnew.Service.Users.Register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.job.onlinejobnew.Dto.*;
import online.job.onlinejobnew.Dto.Register.EmployeeDto;
import online.job.onlinejobnew.Dto.Register.LoginDto;
import online.job.onlinejobnew.Dto.Register.SkillsDto;
import online.job.onlinejobnew.Dto.Register.VerifyDto;
import online.job.onlinejobnew.Entity.Ability.Skills;
import online.job.onlinejobnew.Entity.Category.Category;
import online.job.onlinejobnew.Entity.Category.CategoryType;
import online.job.onlinejobnew.Entity.RoleEntiy.Role;
import online.job.onlinejobnew.Entity.RoleEntiy.RoleName;
import online.job.onlinejobnew.Entity.Users.Employee;
import online.job.onlinejobnew.Repository.Category.CategoryRepository;
import online.job.onlinejobnew.Repository.Category.CategoryTypeRepository;
import online.job.onlinejobnew.Repository.Role.RoleRepository;
import online.job.onlinejobnew.Repository.Skills.SkillRepository;
import online.job.onlinejobnew.Repository.users.EmployeeRepository;
import online.job.onlinejobnew.Service.Users.Register.AllUserServiceInterfaces.EmployeeServiceInterface;
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

import java.util.*;

@Service
public class EmployeeService implements EmployeeServiceInterface {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;
    private final CategoryTypeRepository typeRepository;
    private final SkillRepository skillRepository;
    private final VerifyAllUsers verifyAllUsers;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository, CategoryTypeRepository typeRepository, SkillRepository skillRepository, VerifyAllUsers verifyAllUsers, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
        this.typeRepository = typeRepository;
        this.skillRepository = skillRepository;
        this.verifyAllUsers = verifyAllUsers;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public ApiResponse register(EmployeeDto employeeDto){
        Role found = roleRepository.findByRoleName(RoleName.Employee).orElseThrow(() -> new RuntimeException("Role not found"));
        ApiResponse user = userService.registerUser(employeeDto);
        if (user.isSuccess()){
            Employee employee = new Employee();
            employee.setRole(found);
            employee.setFirstName(employeeDto.getFirstName());
            employee.setLastName(employeeDto.getLastName());
            employee.setEmail(employeeDto.getEmail());
            employee.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
            employee.setEmail(employeeDto.getEmail());
            employee.setPhoneNumber(employeeDto.getPhone());
            employee.setVerificationCode(user.getData().toString());
            employee.setUsername(employeeDto.getUsername());
            Category category = categoryRepository.findByName(employeeDto.getCategoryName()).orElseThrow(() -> new RuntimeException("Category  not found"));
            employee.setCategory(category);
            Employee save = employeeRepository.save(employee);
            return new ApiResponse(HttpStatus.CREATED.value(), "success" ,true,save);
        }
        return new ApiResponse(HttpStatus.CONFLICT.value(), "error" ,false,null);
    }

    @Override
    public ApiResponse setJob(UUID id, String jobType) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Optional<CategoryType> categoryType = typeRepository.findByName(jobType);
            if (categoryType.isPresent()) {
                CategoryType categoryType1 = categoryType.get();
                employee.getCategory().getCategoryType().add(categoryType1);
                employeeRepository.save(employee);
                return new ApiResponse(HttpStatus.OK.value(), "success" ,true,employee);
            }
            CategoryType categoryType1 = new CategoryType();
            categoryType1.setName(jobType);
            CategoryType save = typeRepository.save(categoryType1);
            employee.getCategory().getCategoryType().add(save);
            Employee save1 = employeeRepository.save(employee);
            return new ApiResponse(HttpStatus.OK.value(), "success" ,true,save1);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "user not found" ,false,null);
    }

    @Override
    public ApiResponse setSkills(SkillsDto skillsDto) {
        Optional<Employee> byId = employeeRepository.findById(skillsDto.getId());
        if (byId.isPresent()) {
            Employee employee = byId.get();
            List<String> skills = skillsDto.getSkills();
            List<Skills> skills1 = new ArrayList<>();
            for (String skill : skills) {
                Skills skills2 = new Skills();
                skills2.setName(skill);
                skills1.add(skills2);
            }
            List<Skills> skills2 = skillRepository.saveAll(skills1);
            Set<CategoryType> categoryType = employee.getCategory().getCategoryType();
            for (CategoryType type : categoryType) {
                type.getSkills().addAll(skills2);
            }
            Employee save = employeeRepository.save(employee);
            return new ApiResponse(HttpStatus.OK.value(), "success" ,true,save);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "user not found" ,false,null);
    }

    @Override
    public ApiResponse verify(VerifyDto verifyDto) {
        Optional<Employee> optionalAdmin = employeeRepository.findByEmail(verifyDto.getEmail());
        if (optionalAdmin.isPresent()) {
            return verifyAllUsers.verifyEmployee(optionalAdmin.get(), verifyDto.getCode(), employeeRepository);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "Employee not found", false, null);
    }

    @Override
    public ApiResponse login(LoginDto loginDto) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginDto.getUsername());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails, loginDto.getPassword(), userDetails.getAuthorities())
            );
            if (authentication != null && authentication.isAuthenticated()) {
               Employee employee = (Employee) authentication.getPrincipal();
                String token = jwtProvider.generateToken(employee.getUsername(), employee.getEmail(), employee.getRole());
                return new ApiResponse(HttpStatus.OK.value(), "Employee logged in successfully", true, token);
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
    public ApiResponse resendCode(String email) throws ClassNotFoundException {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new ClassNotFoundException("email not found"));
        String emailCode = GenerateCode.Code(6);
        employee.setVerificationCode(emailCode);
        if (userService.SendEmail(email, emailCode)) {
            employeeRepository.save(employee);
            return new ApiResponse(HttpStatus.OK.value(), "code was sent", true, null);
        }
        return new ApiResponse(HttpStatus.BAD_REQUEST.value(), "email not found", false, null);
    }

    @Override
    public ApiResponse changePassword(ChangePassw changePassw) {
        Optional<Employee> byId = employeeRepository.findById(changePassw.getId());
        if (byId.isPresent()) {
            Employee admin = byId.get();
            if (passwordEncoder.matches(changePassw.getOldPassw(), admin.getPassword())) {
                admin.setPassword(passwordEncoder.encode(changePassw.getNewPassw()));
                Employee save = employeeRepository.save(admin);
                return new ApiResponse(HttpStatus.OK.value(), "Password changed successfully", true, save);
            }
            return new ApiResponse(HttpStatus.UNAUTHORIZED.value(), "Incorrect password", false, null);
        }
        return new ApiResponse(HttpStatus.NOT_FOUND.value(), "Employee not found", false, null);
    }
}
