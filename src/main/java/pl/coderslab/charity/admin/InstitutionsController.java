package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.repos.Donation;
import pl.coderslab.charity.repos.DonationRepository;
import pl.coderslab.charity.repos.Institution;
import pl.coderslab.charity.repos.InstitutionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class InstitutionsController {
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    DonationRepository donationRepository;

    @GetMapping("/admin/institutions")
    public String instView(Model model, Principal principal) {
            if (principal!=null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            model.addAttribute("institutions", institutionRepository.findAll());
            return "admin/admin-institutions";
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

        return "admin/admin-institution-edit";
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
            return "admin/admin-institution-edit";
        }
        institutionRepository.save(institution);

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("institutions", institutionRepository.findAll());
        return "admin/admin-institutions";
    }

    @RequestMapping(value = "/admin/institutions/del", method = RequestMethod.GET)
    public String del(@RequestParam("id") String id, Principal principal, Model model) {
        Institution instTodel = institutionRepository.getOne(Integer.parseInt(id));
        List<Donation> donationsTodel =  donationRepository.findAllByInstitution(instTodel);

        for (Donation d : donationsTodel) {
            donationRepository.delete(d);
        }
        institutionRepository.delete(instTodel);

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("institutions", institutionRepository.findAll());
        return "admin/admin-institutions";
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
        return "admin/admin-institution-edit";
    }
}
