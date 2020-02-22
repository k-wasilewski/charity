package pl.coderslab.charity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.security.UserRepository;
import pl.coderslab.charity.repos.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class HomeController {
@Autowired
private InstitutionRepository institutionRepository;
@Autowired
private DonationRepository donationRepository;
@Autowired
private UserRepository userRepository;

    @RequestMapping("/")
    public String homeAction(Model model, Principal principal, Authentication authentication){
        List<String> roles = new ArrayList<>();
        if (authentication!=null && authentication.getAuthorities()!=null) {
            for (GrantedAuthority i : authentication.getAuthorities()) {
                roles.add(i.getAuthority());
            }
        }

        model.addAttribute("institutions", institutionRepository.findAll());
        model.addAttribute("donationsQuantities", donationRepository.customQuantitiesSum());
        model.addAttribute("donationsSum", donationRepository.findAll().size());
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
            model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        } else {
            model.addAttribute("username", null);
        }
        if (roles.contains("ROLE_ADMIN")) {
            return "admin/admin";
        } else if (roles.contains("ROLE_INSTITUTION")) {
            return "instit/instit";
        }
        return "index";
    }
}
