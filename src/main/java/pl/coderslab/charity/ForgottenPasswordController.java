package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.auth.UserRepository;

@Controller
public class ForgottenPasswordController {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/forgotten")
    public String forgotten() {
        return "forgottenPwd";
    }

    @PostMapping("/forgotten")
    public String sendEmail(Model model, @RequestParam("email") String email) {
        String password = userRepository.getPassword(email);
        if (password==null) {
            model.addAttribute("msg", true);
            return "forgotten-confirmation";
        }
        emailService.sendSimpleMessage(email, "Forgotten password", userRepository.getPassword(email));
        return "forgotten-confirmation";
    }
}
