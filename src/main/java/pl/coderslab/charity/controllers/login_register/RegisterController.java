package pl.coderslab.charity.controllers.login_register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.security.events.OnRegistrationCompleteEvent;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.services.UserService;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RegisterController {
    private UserService userService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    public RegisterController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerView(Model model){
        return "login_register/register";
    }

    @PostMapping("/register")
    public String doRegister(Model model, @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam("password2") String password2,
                                 WebRequest request,
                             @CookieValue(value = "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", required = false) String langCkie) {
        String lang;
        if (langCkie!=null && langCkie.equals("pl")) lang="pl";
        else lang="en";

        if (!password.equals(password2)) {
            model.addAttribute("msg", true);
            return "index";
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        if (!m.find()) {
            model.addAttribute("msg10", true);
            return "index";
        }
        try {
            User userDB = userService.findByUserName(username);
            if (userDB!=null) {
                model.addAttribute("msg2", true);
                return "index";
            } else {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userService.saveUser(user);

                try {
                    String appUrl = request.getContextPath();
                    eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                            (user, request.getLocale(), appUrl, lang));
                    model.addAttribute("msg8", true);
                } catch (Exception me) {
                    model.addAttribute("msg5", true);
                }
            }
        } catch (Exception e) {
            return "fail";
        }

        return "index";
    }

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("msg6", true);
            return "index";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("msg7", true);
            return "index";
        }

        userService.activateUser(user);
        return "index";
    }
}
