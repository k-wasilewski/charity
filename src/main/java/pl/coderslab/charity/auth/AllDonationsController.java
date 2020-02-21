package pl.coderslab.charity.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.security.User;
import pl.coderslab.charity.security.UserRepository;

import java.security.Principal;

@Controller
public class AllDonationsController {
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/auth/all-donations")
    public String allDonationsView(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("donations", donationRepository.findAllByOwner(user));

        return "auth/allDonations";
    }

    @GetMapping("/auth/donation/del")
    public String delDonation(Model model, Principal principal, @RequestParam("id") int id) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        donationRepository.delete(donationRepository.getOne(id));

        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("donations", donationRepository.findAllByOwner(user));

        return "auth/allDonations";
    }
}
