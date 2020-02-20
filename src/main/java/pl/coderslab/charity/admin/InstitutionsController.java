package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.repos.Institution;
import pl.coderslab.charity.repos.InstitutionRepository;

import javax.validation.Valid;
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

    @GetMapping("/admin/institutions/edit")
    public String editView(@RequestParam("id") String idString, Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        int id = Integer.parseInt(idString);
        model.addAttribute("inst", institutionRepository.findById(id));

        return "admin-institution-edit";
    }

    @PostMapping("/admin/instututions/edit")
    public String edit(@Valid @ModelAttribute("inst") Institution institution) {
        return "ok udalo sie";
    }
}
