package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.repos.*;

import java.security.Principal;


@Controller
public class HomeController {
@Autowired
private InstitutionRepository institutionRepository;
@Autowired
private DonationRepository donationRepository;

    @RequestMapping("/")
    public String homeAction(Model model, Principal principal){
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donationsQuantities", donationRepository.customQuantitiesSum());
        model.addAttribute("donationsSum", donationRepository.findAll().size());
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        return "index";
    }
}
