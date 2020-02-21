package pl.coderslab.charity;

import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.auth.UserRepository;
import pl.coderslab.charity.repos.CategoryRepository;
import pl.coderslab.charity.repos.Donation;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.InstitutionRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

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

    @GetMapping("/auth/donation")
    public String donationForm(Model model, HttpServletRequest req, Principal principal){
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

        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "donationForm";
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

            return "donationForm";
        }
        donationRepository.save(donation);
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "redirect:/auth/confirm";
    }

    @RequestMapping("/auth/confirm")
    public String confirm(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        return "form-confirmation";
    }
}
