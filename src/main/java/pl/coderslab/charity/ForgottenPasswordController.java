package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import pl.coderslab.charity.repos.VerificationToken;
import pl.coderslab.charity.security.OnRegistrationCompleteEvent;
import pl.coderslab.charity.security.User;
import pl.coderslab.charity.security.UserRepository;
import pl.coderslab.charity.security.UserService;

import java.security.Principal;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

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
        User user = userRepository.findByUsername(email);
        if (user==null) {
            model.addAttribute("msg", true);
            return "forgotten-confirmation";
        }
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String link = "http://localhost:8081" + "/forgottenConfirm?token=" + token;
        //userService.changePwd(userRepository.findByUsername(email), newPassword);
        emailService.sendSimpleMessage(email, "Forgotten password", link);
        model.addAttribute("msg8", true);
        return "index";
    }

    @GetMapping("/forgottenConfirm")
    public String changePwd(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("msg1", true);
            return "changePwd";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("msg2", true);
            return "changePwd";
        }

        model.addAttribute("username", user.getUsername());
        return "changePwd";
    }

    @PostMapping("changePassword")
    public String doChangePwd(Model model, @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("password2") String password2,
                             WebRequest request) {
        if (!password.equals(password2)) {
            model.addAttribute("msg3", true);
            return "changePwd";
        }
        try {
            User user = userService.findByUserName(username);
            user.setPassword(password);
            userService.saveUser(user);
            userService.activateUser(user);
        } catch (Exception e) {
            model.addAttribute("msg4", true);
            return "changePwd";
        }
        model.addAttribute("msg9", true);
        return "index";
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
