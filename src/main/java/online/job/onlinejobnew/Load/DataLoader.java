package online.job.onlinejobnew.Load;

import online.job.onlinejobnew.Entity.Category.Category;
import online.job.onlinejobnew.Entity.Category.Enums.CategoryName;
import online.job.onlinejobnew.Entity.RoleEntiy.Role;
import online.job.onlinejobnew.Entity.RoleEntiy.RoleName;
import online.job.onlinejobnew.Entity.Users.Client;
import online.job.onlinejobnew.Entity.Users.Employee;
import online.job.onlinejobnew.Repository.Category.CategoryRepository;
import online.job.onlinejobnew.Repository.Role.RoleRepository;
import online.job.onlinejobnew.Repository.users.ClientRepository;
import online.job.onlinejobnew.Repository.users.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    private String initMode;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, ClientRepository clientRepository, CategoryRepository categoryRepository) {
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (initMode.equals("always")) {
            Role admin = new Role();
            admin.setRoleName(RoleName.Admin);
            roleRepository.save(admin);
            Role client = new Role();
            client.setRoleName(RoleName.Client);
            Role clientRole = roleRepository.save(client);
            Role employee = new Role();
            employee.setRoleName(RoleName.Employee);
            Role employeeRole = roleRepository.save(employee);


            Category category = new Category();
            category.setName(CategoryName.Developer);
            Category dev = categoryRepository.save(category);


            Employee employee2 = new Employee();
            employee2.setUsername("employee2");
            employee2.setPassword(passwordEncoder.encode("employee2"));
            employee2.setRole(employeeRole);
            employee2.setEmail("employee2@gmail.com");
            employee2.setPhoneNumber("+998912563478");
            employee2.setCategory(dev);
            employee2.setFirstName("John");
            employee2.setLastName("Smith");
            employeeRepository.save(employee2);

            Client client2 = new Client();
            client2.setUsername("client2");
            client2.setPassword(passwordEncoder.encode("client2"));
            client2.setRole(clientRole);
            client2.setEmail("client2@gmail.com");
            client2.setPhoneNumber("+998912568478");
            client2.setFirstName("Mark");
            client2.setLastName("Smith");
            clientRepository.save(client2);


        }
    }
}
