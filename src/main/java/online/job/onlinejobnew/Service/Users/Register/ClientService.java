package online.job.onlinejobnew.Service.Users.Register;

import online.job.onlinejobnew.Dto.ApiResponse;
import online.job.onlinejobnew.Dto.Register.UserDto;
import online.job.onlinejobnew.Entity.RoleEntiy.Role;
import online.job.onlinejobnew.Entity.RoleEntiy.RoleName;
import online.job.onlinejobnew.Entity.Users.Client;
import online.job.onlinejobnew.Repository.Role.RoleRepository;
import online.job.onlinejobnew.Repository.users.ClientRepository;
import online.job.onlinejobnew.Service.Users.Register.CloneUserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientService(ClientRepository clientRepository, RoleRepository roleRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    public ApiResponse register(UserDto userDto) throws ClassNotFoundException {
        Role role = roleRepository.findByRoleName(RoleName.Client).orElseThrow(() -> new RuntimeException("Role not found"));
        ApiResponse user = userService.registerUser(userDto);
        if (user.isSuccess()){
            Client client = new Client();
            client.setRole(role);
            client.setUsername(userDto.getUsername());
            client.setPassword(passwordEncoder.encode(userDto.getPassword()));
            client.setEmail(userDto.getEmail());
            client.setFirstName(userDto.getFirstName());
            client.setLastName(userDto.getLastName());
            client.setPhoneNumber(userDto.getPhone());
            client.setVerificationCode(user.getData().toString());
            Client save = clientRepository.save(client);
            return new ApiResponse(HttpStatus.OK.value(), "success" , true ,save);
        }
        return new ApiResponse(HttpStatus.REQUEST_TIMEOUT.value(), "server error" , false ,null);
    }
}
