package pl.coderslab.charity.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;
import pl.coderslab.charity.security.services.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AdminsController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/admin/admins")
    public String instView(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("admins", userRepository.findByRoles_Id(2));
        return "admin/admin-admins";
    }

    @GetMapping("/admin/admins/edit")
    public String editView(@RequestParam("id") String idString, Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        Long id = Long.parseLong(idString);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("admin", user.get());
        }

        return "admin/admin-admins-edit";
    }

    @RequestMapping(value = "/admin/admins/edit", method = RequestMethod.POST)
    public String edit(Model model, Principal principal, @RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("password2") String password2,
                       @RequestParam("id") Long id) {
        if (!password.equals(password2)) {
            if (principal!=null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            model.addAttribute("msg", true);
            return "admin/admin-admins-edit";
        }
        try {
            if (id!=null) {
                Optional<User> user = userRepository.findById(id);
                if (user.isPresent()) {
                    User actualUser = user.get();
                    actualUser.setUsername(username);
                    actualUser.setPassword(password);
                    userService.saveAdmin(actualUser);
                }
            } else {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                userService.saveAdmin(user);
            }
        } catch (Exception e) {
            return "fail";
        }

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("admins", userRepository.findByRoles_Id(2));
        return "admin/admin-admins";
    }

    @RequestMapping(value = "/admin/admins/del", method = RequestMethod.GET)
    public String del(@RequestParam("id") String id, Principal principal, Model model) {
        String currentUsername = principal.getName();
        String usernameToDel = userRepository.getOne(Long.parseLong(id)).getUsername();

        if (currentUsername.equals(usernameToDel)) {
            model.addAttribute("msg", true);
            return "admin/admin-admins";
        }

        userRepository.delete(userRepository.getOne(Long.parseLong(id)));

        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("admins", userRepository.findByRoles_Id(2));
        return "admin/admin-admins";
    }

    @GetMapping("/admin/admins/add")
    public String addInstView(Model model, Principal principal) {
        if (principal!=null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        model.addAttribute("admin", new User());
        model.addAttribute("add", true);
        return "admin/admin-admins-edit";
    }
}
