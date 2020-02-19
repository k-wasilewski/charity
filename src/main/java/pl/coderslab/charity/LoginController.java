package pl.coderslab.charity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.auth.User;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String donationForm(Model model){
        return "login";
    }

}
