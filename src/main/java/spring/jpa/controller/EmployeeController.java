package spring.jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import spring.jpa.model.Employee;
import spring.jpa.repository.UserRepository;

@Controller
@RequestMapping(value = "/produit")
public class EmployeeController {

    @Autowired
    private UserRepository userRepository; // Use UserRepository instead of EmployeRepository

    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<Employee> lp = userRepository.findAll(); // Use userRepository.findAll() instead of employeRepos.findAll()
        model.addAttribute("employe", lp);
        return "produits";
    }
}
