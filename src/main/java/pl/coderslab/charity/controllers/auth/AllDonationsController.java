package pl.coderslab.charity.controllers.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.repos.Donation;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.security.User;
import pl.coderslab.charity.security.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Donation> sortedDonations = donationRepository.findAllByOwner(user).stream()
                .sorted((a, b)->{
                    int pickedUp = (a.getPickedUp()>b.getPickedUp() ? 1 : -1);
                    if (a.getPickedUp()==b.getPickedUp()) pickedUp=0;
                    if (pickedUp!=0) return pickedUp;

                    int pickUpDate = (a.getPickUpDate().compareTo(b.getPickUpDate()));
                    if (a.getPickUpDate()==b.getPickUpDate()) pickUpDate=0;
                    if (pickUpDate!=0) return pickUpDate;

                    return (a.getCreated().compareTo(b.getCreated()));
                })
                .collect(Collectors.toList());

        model.addAttribute("donations", sortedDonations);

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

        List<Donation> sortedDonations = donationRepository.findAllByOwner(user).stream()
                .sorted((a, b)->{
                    int pickedUp = (a.getPickedUp()>b.getPickedUp() ? 1 : -1);
                    if (a.getPickedUp()==b.getPickedUp()) pickedUp=0;
                    if (pickedUp!=0) return pickedUp;

                    int pickUpDate = (a.getPickUpDate().compareTo(b.getPickUpDate()));
                    if (a.getPickUpDate()==b.getPickUpDate()) pickUpDate=0;
                    if (pickUpDate!=0) return pickUpDate;

                    return (a.getCreated().compareTo(b.getCreated()));
                })
                .collect(Collectors.toList());

        model.addAttribute("donations", sortedDonations);

        return "auth/allDonations";
    }
}
