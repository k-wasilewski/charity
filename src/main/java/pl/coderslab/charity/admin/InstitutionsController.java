package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.coderslab.charity.repos.InstitutionRepository;

import java.security.Principal;

@Controller
public class InstitutionsController {
    @Autowired
    InstitutionRepository institutionRepository;

    @GetMapping("/admin/institutions")
    public String instView(Model model, Principal principal) {
            if (principal!=null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            model.addAttribute("institutions", institutionRepository.findAll());
            return "admin-institutions";
    }
}
