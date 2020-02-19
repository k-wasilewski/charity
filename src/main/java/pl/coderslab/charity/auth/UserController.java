package pl.coderslab.charity.auth;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    @ResponseBody
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.saveUser(user);
        } catch (Exception e) {
            return "fail";
        }
        return "success";
    }

    @RequestMapping(value = "/auth/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
}
