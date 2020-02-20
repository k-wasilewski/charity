package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value = "/admin/instututions/edit", method = RequestMethod.POST)
    public String edit(@ModelAttribute("inst") @Valid Institution institution, BindingResult result,
                       Principal principal, Model model) {
        if (result.hasErrors()) {
            if (principal!=null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            model.addAttribute("inst", institution);
            return "admin-institution-edit";
        }
        institutionRepository.save(institution);

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("institutions", institutionRepository.findAll());
        return "admin-institutions";
    }

    @RequestMapping(value = "/admin/institutions/del", method = RequestMethod.GET)
    public String del(@RequestParam("id") String id, Principal principal, Model model) {
        institutionRepository.delete(institutionRepository.getOne(Integer.parseInt(id)));

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("institutions", institutionRepository.findAll());
        return "admin-institutions";
    }

    @GetMapping("/admin/institutions/add")
    public String addInstView(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        model.addAttribute("inst", new Institution());
        model.addAttribute("add", true);
        return "admin-institution-edit";
    }
}
