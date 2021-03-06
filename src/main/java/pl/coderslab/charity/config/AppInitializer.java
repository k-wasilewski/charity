package pl.coderslab.charity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.repos.InstitutionRepository;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.services.UserService;

import javax.annotation.PostConstruct;

@Component
class AppInitializer {
    @Autowired
    UserService userService;
    @Autowired
    InstitutionRepository institutionRepository;

    @PostConstruct
    private void init() {
        User admin1 = new User();
        admin1.setUsername("admin1@admin.pl");
        admin1.setPassword("admin");
        userService.saveAdmin(admin1);

        User admin2 = new User();
        admin2.setUsername("admin2@admin.pl");
        admin2.setPassword("admin");
        userService.saveAdmin(admin2);

        User isntit = new User();
        isntit.setUsername("dbamozdrowie@wp.pl");
        isntit.setPassword("instit");
        isntit.setInstitution(institutionRepository.findById(1));
        userService.saveInstitution(isntit);

        User user = new User();
        user.setUsername("test@test.pl");
        user.setPassword("test");
        userService.saveUser(user);
        userService.activateUser(user);
    }
}
