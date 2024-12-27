package online.job.onlinejobnew.Service.Users.Works.Interfaces;

import online.job.onlinejobnew.Dto.ApiResponse;

public interface EmployeeInterface {
    ApiResponse setProfile();
    ApiResponse getProfile();
    ApiResponse deleteProfile();
    ApiResponse updateProfile();
    ApiResponse TakeOrder();
    ApiResponse getTakeOrder();

}
