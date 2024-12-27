package online.job.onlinejobnew.Service.Users.Register.AllUserServiceInterfaces;

import online.job.onlinejobnew.Dto.ApiResponse;
import online.job.onlinejobnew.Dto.Register.UserDto;

public interface UserRegisterInterface {

    ApiResponse registerUser(UserDto user);

}
