package com.agk.crm.boot;


import com.agk.crm.entity.UserEntity;
import com.agk.crm.entity.UserRole;
import com.agk.crm.repository.RoleRepository;
import com.agk.crm.repository.UsersRepository;
import com.agk.crm.service.impl.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartRunner implements CommandLineRunner {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public ApplicationStartRunner() {
    }

    @Override
    public void run(String... args) throws Exception {


        if (usersService.getByUser("admin") == null) {
            UserEntity admin = new UserEntity();
            admin.setLogin("admin");
            admin.setPassword( passwordEncoder.encode("admin"));
            admin.setIsActive(1L);
            admin.setEmail("Admin.Mail.Ru");
            admin = usersRepository.save(admin);

            UserRole userRole = new UserRole();
            userRole.setRoleName("ROLE_ADMIN");
            userRole.setUserEntity(admin);
            roleRepository.save(userRole);
        }

//        mailService.send("89515014507@mail.ru", "Online Shop Nikolas", "Программа запущенна");


    }
}
