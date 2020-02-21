package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.auth.UserRepository;
import pl.coderslab.charity.auth.UserService;

import java.security.Principal;
import java.util.Random;

@Controller
public class ForgottenPasswordController {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/forgotten")
    public String forgotten(Model model, Principal principal) {
        return "forgottenPwd";
    }

    @PostMapping("/forgotten")
    public String sendEmail(Model model, @RequestParam("email") String email, Principal principal) {
        String password = userRepository.getPassword(email);
        if (password==null) {
            model.addAttribute("msg", true);
            return "forgotten-confirmation";
        }
        String newPassword = generateRandomSpecialCharacters(8);
        userService.changePwd(userRepository.findByUsername(email), newPassword);
        emailService.sendSimpleMessage(email, "Forgotten password", newPassword);
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "forgotten-confirmation";
    }

    public String generateRandomSpecialCharacters(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
