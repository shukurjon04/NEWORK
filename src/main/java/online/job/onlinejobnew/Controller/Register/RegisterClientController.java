package online.job.onlinejobnew.Controller.Register;

import online.job.onlinejobnew.Dto.ApiResponse;
import online.job.onlinejobnew.Dto.Register.UserDto;
import online.job.onlinejobnew.Service.Users.Register.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class RegisterClientController {

    private final ClientService clientService;

    @Autowired
    public RegisterClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody UserDto userDto) throws ClassNotFoundException {
        ApiResponse register = clientService.register(userDto);
        return ResponseEntity.status(register.getStatus()).body(register);
    }
}
