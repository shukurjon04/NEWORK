package online.job.onlinejobnew.Controller.Register;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.job.onlinejobnew.Dto.*;
import online.job.onlinejobnew.Dto.Register.LoginDto;
import online.job.onlinejobnew.Dto.Register.UserDto;
import online.job.onlinejobnew.Dto.Register.VerifyDto;
import online.job.onlinejobnew.Service.Users.Register.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class RegisterAdminController {

    private final AdminService adminService;

    @Autowired
    public RegisterAdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDto userDto) throws ClassNotFoundException {
        ApiResponse register = adminService.register(userDto);
        return ResponseEntity.status(register.getStatus()).body(register);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAdmin(@RequestBody VerifyDto verifyDto)  {
        ApiResponse verify = adminService.verify(verifyDto);
        System.out.println(verify.getStatus());
        System.out.println(verify.getMessage());
        System.out.println(verify.getData());
        System.out.println(verify.isSuccess());
        return ResponseEntity.status(verify.getStatus()).body(verify);
    }

    @PostMapping("/resend/code")
    public ResponseEntity<?> resendCode(@RequestParam String email) throws Exception {
        ApiResponse apiResponse = adminService.resendCode(email);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody LoginDto loginDto)  {
        ApiResponse apiResponse = adminService.login(loginDto);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
        ApiResponse logout = adminService.logout(request, response);
        return ResponseEntity.status(logout.getStatus()).body(logout);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> change(@RequestBody ChangePassw passw) {
        ApiResponse apiResponse = adminService.changePassword(passw);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<?> getEmployees() {
        ApiResponse allEmployees = adminService.getALLEmployees();
        return ResponseEntity.status(allEmployees.getStatus()).body(allEmployees);
    }
    @GetMapping("/getClients")
    public ResponseEntity<?> getClient() {
        ApiResponse allClients = adminService.getAllClients();
        return ResponseEntity.status(allClients.getStatus()).body(allClients);
    }
    @GetMapping("/getAdmins")
    public ResponseEntity<?> getAdmins() {
        ApiResponse allAdmins = adminService.getAllAdmins();
        return ResponseEntity.status(allAdmins.getStatus()).body(allAdmins);
    }
    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<?> deleteEmployee(@RequestParam UUID id){
        ApiResponse apiResponse = adminService.deleteEmployee(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @DeleteMapping("/deleteClient")
    public ResponseEntity<?> deleteClient(@RequestParam UUID id){
        ApiResponse apiResponse = adminService.deleteClient(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @GetMapping("/getClient/{id}")
    public ResponseEntity<?> getClient(@PathVariable UUID id){
        ApiResponse apiResponse = adminService.getClient(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable UUID id){
        ApiResponse apiResponse = adminService.getEmployee(id);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

}
