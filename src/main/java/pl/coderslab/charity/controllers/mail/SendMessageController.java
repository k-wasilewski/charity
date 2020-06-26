package pl.coderslab.charity.controllers.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.coderslab.charity.services.EmailService;

@Controller
public class SendMessageController {
    @Autowired
    EmailService emailService;

    @PostMapping("/msg")
    public ModelAndView doSendMsg(@RequestParam("name") String name, @RequestParam("surname") String surname,
                            @RequestParam("message") String message) {
        emailService.sendSimpleMessage("charitydonation70@gmail.com", name+" "+surname, message);
        return new ModelAndView("redirect:/");
    }
}
