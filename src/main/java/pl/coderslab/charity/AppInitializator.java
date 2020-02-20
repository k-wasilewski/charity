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
        User user = new User();
        user.setUsername("admin@admin.pl");
        user.setPassword("admin");
        userService.saveAdmin(user);
    }
}
