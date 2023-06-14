package project.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.ecommerce.model.UserInfo;
import project.ecommerce.service.UserService;


@Controller
public class AuthController {
    @Autowired
    private UserService service;

    @GetMapping("/login")
    public String loginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // User is authenticated
            String username = authentication.getName(); // Get the username
            // Perform actions for authenticated user
            model.addAttribute("username", username);
        }
        return "login";
    }


    @GetMapping("/failedLogin")
    public String failedloginPage(Model model) {
        String message = "Invalid Username or password";
        model.addAttribute("message",message);
        return "login";
    }


    @PostMapping("/register")
    public String addNewUser(@ModelAttribute("userInfo") UserInfo userInfo , RedirectAttributes redirectAttributes) {
        if (userInfo.getRoles() == null || userInfo.getRoles().isEmpty()) {
            userInfo.setRoles("ROLE_USER");
        }

        service.addUser(userInfo);
        String message = "User registered Successfully";
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/user/addUser";
    }
}
