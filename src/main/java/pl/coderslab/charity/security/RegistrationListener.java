package pl.coderslab.charity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.services.EmailService;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    @Autowired
    private UserService service;

    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getUsername();
        String subject = "\"Pass in good hands\" - your activation link";
        String message = "\"Pass in good hands\" - this is your activation link: ";
        if (event.getLang().equals("pl")) {
            subject = "\"Oddam w dobre ręce\" - Twój link aktywacyjny";
            message = "\"Oddam w dobre ręce\" - oto Twój link aktywacyjny: ";
        }
        String confirmationUrl
                = event.getAppUrl() + "/regitrationConfirm?token=" + token;
        String actualMessage = message + "http://localhost:8081" + confirmationUrl;

        emailService.sendSimpleMessage(recipientAddress, subject, actualMessage);
    }
}
