package online.job.onlinejobnew.Service.Users.Register.AllUserServiceInterfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.job.onlinejobnew.Dto.*;
import online.job.onlinejobnew.Dto.Register.EmployeeDto;
import online.job.onlinejobnew.Dto.Register.LoginDto;
import online.job.onlinejobnew.Dto.Register.SkillsDto;
import online.job.onlinejobnew.Dto.Register.VerifyDto;

import java.util.UUID;

public interface EmployeeServiceInterface {
    ApiResponse register(EmployeeDto employeeDto);
    ApiResponse setJob(UUID id , String job);
    ApiResponse setSkills(SkillsDto skillsDto);
    ApiResponse verify(VerifyDto verifyDto);
    ApiResponse login(LoginDto loginDto);
    ApiResponse logout(HttpServletRequest request, HttpServletResponse response);
    ApiResponse resendCode(String email) throws ClassNotFoundException;
    ApiResponse changePassword(ChangePassw changePassw);

}
