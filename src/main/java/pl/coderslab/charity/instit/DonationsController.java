package pl.coderslab.charity.instit;

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
public class DonationsController {
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/instit/donations")
    public String myDonations(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);

        List<Donation> sortedDonations = donationRepository.findAllByInstitution(user.getInstitution()).stream()
                .sorted((a, b) -> {
                    int pickedUp = (a.getPickedUp() > b.getPickedUp() ? 1 : -1);
                    if (a.getPickedUp() == b.getPickedUp()) pickedUp = 0;
                    if (pickedUp != 0) return pickedUp;

                    int pickUpDate = (a.getPickUpDate().compareTo(b.getPickUpDate()));
                    if (a.getPickUpDate() == b.getPickUpDate()) pickUpDate = 0;
                    if (pickUpDate != 0) return pickUpDate;

                    return (a.getCreated().compareTo(b.getCreated()));
                })
                .collect(Collectors.toList());

        model.addAttribute("donations", sortedDonations);

        return "instit/myDonations";
    }

    @GetMapping("/instit/donation/pickedupOn")
    public String pickedupOn(Model model, Principal principal, @RequestParam("id") int id) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        Donation don = donationRepository.getOne(id);
        don.setPickedUp(1);
        donationRepository.save(don);

        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);

        List<Donation> sortedDonations = donationRepository.findAllByInstitution(user.getInstitution()).stream()
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

        return "instit/myDonations";
    }

    @GetMapping("/instit/donation/pickedupOff")
    public String pickedupOff(Model model, Principal principal, @RequestParam("id") int id) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        Donation don = donationRepository.getOne(id);
        don.setPickedUp(0);
        donationRepository.save(don);

        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);

        List<Donation> sortedDonations = donationRepository.findAllByInstitution(user.getInstitution()).stream()
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

        return "instit/myDonations";
    }
}
