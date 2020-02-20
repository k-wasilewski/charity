package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.auth.User;
import pl.coderslab.charity.auth.UserService;

import javax.annotation.PostConstruct;

@Component
class AppInitializator {
    @Autowired
    UserService userService;

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
    }
}
