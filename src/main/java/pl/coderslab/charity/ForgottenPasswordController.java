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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String subject = "\"Oddam w dobre ręce\" - Twój link do zmiany hasła";

        String link = "http://localhost:8081" + "/forgottenConfirm?token=" + token;
        emailService.sendSimpleMessage(email, subject, link);
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
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (!m.find()) {
            model.addAttribute("msg10", true);
            return "index";
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
}
