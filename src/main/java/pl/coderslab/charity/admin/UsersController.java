package pl.coderslab.charity.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.auth.User;
import pl.coderslab.charity.auth.UserRepository;
import pl.coderslab.charity.auth.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UsersController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/admin/users")
    public String instView(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("users", userRepository.findByRoles_Id(1));
        return "admin/admin-users";
    }

    @GetMapping("/admin/users/edit")
    public String editView(@RequestParam("id") String idString, Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        Long id = Long.parseLong(idString);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        }

        return "admin/admin-users-edit";
    }

    @RequestMapping(value = "/admin/users/edit", method = RequestMethod.POST)
    public String edit(Model model, Principal principal, @RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("password2") String password2,
                       @RequestParam("id") Long id) {
        if (!password.equals(password2)) {
            if (principal != null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            model.addAttribute("msg", true);
            return "admin/admin-users-edit";
        }
        try {
            if (id != null) {
                Optional<User> user = userRepository.findById(id);
                if (user.isPresent()) {
                    User actualUser = user.get();
                    actualUser.setUsername(username);
                    actualUser.setPassword(password);
                    userService.saveUser(actualUser);
                }
            } else {
                if (userService.findByUserName(username)!=null) {
                    model.addAttribute("msg2", true);
                    return "admin/admin-users-edit";
                } else {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    userService.saveUser(user);
                }
            }
        } catch (Exception e) {
            return "fail";
        }

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("users", userRepository.findByRoles_Id(1));
        return "admin/admin-users";
    }

    @RequestMapping(value = "/admin/users/del", method = RequestMethod.GET)
    public String del(@RequestParam("id") String id, Principal principal, Model model) {
        userRepository.delete(userRepository.getOne(Long.parseLong(id)));

        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }
        model.addAttribute("users", userRepository.findByRoles_Id(1));
        return "admin/admin-users";
    }

    @GetMapping("/admin/users/add")
    public String addInstView(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", null);
        }

        model.addAttribute("user", new User());
        model.addAttribute("add", true);
        return "admin/admin-users-edit";
    }

    @RequestMapping(value = "/admin/users/details", method = RequestMethod.GET)
    public String detailsView(@RequestParam("id") String id, Principal principal, Model model) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if (user.isPresent()) {
            User actualUser = user.get();

            if (principal != null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }

            model.addAttribute("user", actualUser);
        }
        return "admin/admin-users-details";
    }

    @RequestMapping(value = "/admin/users/block", method = RequestMethod.GET)
    public String block(@RequestParam("id") String id, Principal principal, Model model) {
        Optional<User> user = userRepository.findById(Long.parseLong(id));
        if (user.isPresent()) {
            User actualUser = user.get();

            if (principal != null) {
                model.addAttribute("username", principal.getName());
            } else {
                model.addAttribute("username", null);
            }
            actualUser.setBlocked(1);
            userRepository.save(actualUser);
        }
        model.addAttribute("users", userRepository.findByRoles_Id(1));
        return "admin/admin-users";
    }
}
