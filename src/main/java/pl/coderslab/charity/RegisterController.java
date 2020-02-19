package pl.coderslab.charity;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.auth.User;
import pl.coderslab.charity.auth.UserService;
import pl.coderslab.charity.repos.Donation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegisterController {
    private UserService userService;

    public RegisterController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String donationForm(Model model){
        return "register";
    }

    @PostMapping("/register")
    public String donationAction(Model model, @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("password2") String password2) {
        if (!password.equals(password2)) model.addAttribute("msg", true);
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.saveUser(user);
        } catch (Exception e) {
            return "fail";
        }
        return "redirect:/";
    }
}
