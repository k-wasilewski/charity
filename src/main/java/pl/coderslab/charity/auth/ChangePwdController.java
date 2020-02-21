package pl.coderslab.charity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.security.User;
import pl.coderslab.charity.security.UserRepository;
import pl.coderslab.charity.security.UserService;

import java.security.Principal;

@Controller
public class ChangePwdController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/auth/changePwd")
    public String changePwd(Principal principal, Model model) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "auth/changePwd";
    }

    @PostMapping("/auth/changePwd")
    public String actualChangePwd(Model model, @RequestParam("old-pwd") String oldPwd,
                                  @RequestParam("new-pwd") String newPwd,
                                  @RequestParam("new-pwd2") String newPwd2,
                                  Principal principal) {
        String username = "";
        if (principal!=null) {
            username = principal.getName();
        }

        User currentUser = userRepository.findByUsername(username);

        if (userService.confirmPwd(username, oldPwd) && newPwd.equals(newPwd2)) {
            model.addAttribute("username", username);
            try {
                userService.changePwd(currentUser, newPwd);
                model.addAttribute("msg4", true);
            } catch (Exception e) {
                model.addAttribute("msg3", true);
            }
        } else if (!newPwd.equals(newPwd2)){
            model.addAttribute("username", username);
            model.addAttribute("msg", true);
        } else if (!userService.confirmPwd(username, oldPwd)) {
            model.addAttribute("username", username);
            model.addAttribute("msg2", true);
        }
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "index";
    }
}
