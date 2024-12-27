package online.job.onlinejobnew.Service.Users.Register.AllUserServiceInterfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import online.job.onlinejobnew.Dto.*;
import online.job.onlinejobnew.Dto.Register.LoginDto;
import online.job.onlinejobnew.Dto.Register.UserDto;
import online.job.onlinejobnew.Dto.Register.VerifyDto;

import java.util.UUID;

public interface AdminServiceInterface {
    ApiResponse register(UserDto userDto) throws Exception;
    ApiResponse verify(VerifyDto verifyDto) throws Exception;
    ApiResponse resendCode(String email) throws Exception;
    ApiResponse login(LoginDto loginDto) throws Exception;
    ApiResponse logout(HttpServletRequest request, HttpServletResponse response) throws Exception;
    ApiResponse changePassword(ChangePassw passw) throws Exception;
    ApiResponse getALLEmployees() throws Exception;
    ApiResponse getAllClients() throws Exception;
    ApiResponse getAllAdmins() throws Exception;
    ApiResponse deleteEmployee(UUID id) throws Exception;
    ApiResponse deleteClient(UUID id) throws Exception;
    ApiResponse getEmployee(UUID id) throws Exception;
    ApiResponse getClient(UUID id) throws Exception;
}
