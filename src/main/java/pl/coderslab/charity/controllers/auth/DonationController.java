package pl.coderslab.charity.controllers.auth;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.security.repos.UserRepository;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.entities.Donation;
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
    private String msgsInstToUser = "";

    @KafkaListener(topics = "inst-to-user", groupId = "group-id")
    public void listenInstToUser(String message) {
        msgsInstToUser = message;
    }

    @GetMapping("auth/msg")
    @ResponseBody
    public String getMsg() {
        return msgsInstToUser;
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
                                 Model model, HttpServletRequest req, Principal principal) {
        if (result.hasErrors()) {
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

        kafkaProducerConfig.sendMessageUserToInst("username:"+principal.getName() +
                    ", institution:" + donation.getInstitution() + ", quantity:" + donation.getQuantity() +
                    ", things:"+donation.getCategories());


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
