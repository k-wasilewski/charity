package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.repos.*;


@Controller
public class HomeController {
@Autowired
private InstitutionRepository institutionRepository;
@Autowired
private DonationRepository donationRepository;

    @RequestMapping("/")
    public String homeAction(Model model){
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donationsQuantities", donationRepository.customQuantitiesSum());
        return "index";
    }
}
