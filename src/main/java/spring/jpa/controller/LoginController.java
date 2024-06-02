package spring.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import spring.jpa.dto.LoginForm;
import spring.jpa.model.Admin;
import spring.jpa.model.Conge;
import spring.jpa.model.Employee;
import spring.jpa.model.User;
import spring.jpa.repository.CongeRepository;
import spring.jpa.repository.EmployeeRepository;
import spring.jpa.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CongeRepository congeRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        User foundUser = userRepository.findByLoginAndPassword(loginForm.getLogin(), loginForm.getPassword());

        if (foundUser != null) {
            if (foundUser instanceof Employee) {
                Employee employee = (Employee) foundUser;
                session.setAttribute("userId", employee.getId());
                return "redirect:/conges/employee/home";
            } else if (foundUser instanceof Admin) {
                return "redirect:/admin";
            }
        } else {
            model.addAttribute("error", true);
            return "login";
        }

        return "login";
    }
}
