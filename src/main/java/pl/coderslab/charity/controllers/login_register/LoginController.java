package pl.coderslab.charity.controllers.login_register;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String donationForm(Model model){
        return "login_register/login";
    }

}
