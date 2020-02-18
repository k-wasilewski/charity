package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.repos.Donation;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;

@Controller
public class DonationController {
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private InstitutionRepository institutionRepository;

    @GetMapping("/donation")
    public String donationForm(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donation", new Donation());
        return "donationForm";
    }

    @PostMapping("/donation")
    public String donationAction(@ModelAttribute("donation") Donation donation, Model model){

        return "donationForm";
    }
}
