package online.job.onlinejobnew.Controller.Register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.job.onlinejobnew.Dto.*;
import online.job.onlinejobnew.Dto.Register.EmployeeDto;
import online.job.onlinejobnew.Dto.Register.LoginDto;
import online.job.onlinejobnew.Dto.Register.SkillsDto;
import online.job.onlinejobnew.Dto.Register.VerifyDto;
import online.job.onlinejobnew.Service.Users.Register.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class RegisterEmployeeController {


    private final EmployeeService employeeService;

    @Autowired
    public RegisterEmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeDto employeeDto)  {
        ApiResponse register = employeeService.register(employeeDto);
        return ResponseEntity.status(register.getStatus()).body(register);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyDto verifyDto){
        ApiResponse verify = employeeService.verify(verifyDto);
        return ResponseEntity.status(verify.getStatus()).body(verify);
    }
    @PostMapping("/setJob")
    public ResponseEntity<?> setJob(@RequestParam UUID id , @RequestParam String job){
        ApiResponse apiResponse = employeeService.setJob(id, job);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PostMapping("/setSkill")
    public ResponseEntity<?> setSkill(@RequestBody SkillsDto skillsDto){
        ApiResponse apiResponse = employeeService.setSkills(skillsDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = employeeService.login(loginDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request , HttpServletResponse response){
        ApiResponse logout = employeeService.logout(request, response);
        return ResponseEntity.status(logout.getStatus()).body(logout);
    }
    @PostMapping("/resendCode")
    public ResponseEntity<?> resend(@RequestParam String email) throws ClassNotFoundException {
        ApiResponse apiResponse = employeeService.resendCode(email);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassw(@RequestBody ChangePassw changePassw){
        ApiResponse apiResponse = employeeService.changePassword(changePassw);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
