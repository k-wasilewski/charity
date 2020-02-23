package pl.coderslab.charity.auth;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.KafkaProducerConfig;
import pl.coderslab.charity.security.UserRepository;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.repos.Donation;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class DonationController {
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

    @KafkaListener(topics = "inst-to-user", groupId = "group-id")
    public void listenInstToUser(String message, Model model) {
        System.out.println("Received message at user: " + message);
        model.addAttribute("kafka-msg", message);
    }

    @GetMapping("/auth/donation")
    public String donationForm(Model model, HttpServletRequest req, Principal principal,
                               @RequestParam(value = "id", required = false, defaultValue = "0") int id){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donation", new Donation());

        String userAgent=req.getHeader("user-agent");
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        model.addAttribute("browser", ua.getBrowser().toString());

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        if (id!=0) {
            model.addAttribute("donation", donationRepository.findById(id));
        }
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "auth/donationForm";
    }

    @PostMapping("/auth/donation")
    public String donationAction(@ModelAttribute("donation") @Valid Donation donation, BindingResult result,
                                 Model model, HttpServletRequest req, Principal principal,
                                 @CookieValue(value = "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE", required = false) String langCkie) {
        if (result.hasErrors()) {
            System.out.println(result);
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("institutions", institutionRepository.findAll());
            model.addAttribute("donation", donation);
            model.addAttribute("msg", true);

            String userAgent=req.getHeader("user-agent");
            UserAgent ua = UserAgent.parseUserAgentString(userAgent);
            model.addAttribute("browser", ua.getBrowser().toString());

            if (principal!=null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            model.addAttribute("user", userRepository.findByUsername(principal.getName()));
            return "auth/donationForm";
        }
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        donation.setCreated(LocalDate.now());
        donationRepository.save(donation);

        if (langCkie.equals("pl")) {
            kafkaProducerConfig.sendMessageUserToInst("Użytkownik " + principal.getName() + " przekazał " +
                    "dla instytucji " + donation.getInstitution() + donation.getQuantity()+" " +
                    "worków, a w nich "+donation.getCategories());
        } else {
            kafkaProducerConfig.sendMessageUserToInst("User " + principal.getName() + " has made a donation of" +
                            donation.getQuantity() + " bags with " + donation.getCategories() +
                    "for institution " + donation.getInstitution());
        }

        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "auth/form-confirmation";
    }

    @RequestMapping("/auth/confirm")
    public String confirm(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "auth/form-confirmation";
    }
}
