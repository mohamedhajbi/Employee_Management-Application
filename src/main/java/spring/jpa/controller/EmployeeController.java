package spring.jpa.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.validation.Valid;
import spring.jpa.dto.CongeForm;
import spring.jpa.enumeration.EtatConge;
import spring.jpa.model.Conge;
import spring.jpa.model.Employee;
import spring.jpa.repository.CongeRepository;
import spring.jpa.repository.EmployeeRepository;
import spring.jpa.repository.UserRepository;

@Controller
@RequestMapping(value = "/conges")
public class EmployeeController {

    @Autowired
    private CongeRepository congeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        List<Conge> congeAll = congeRepository.findAll();
        model.addAttribute("pageConges", congeAll);
        return "conges";
    }

    @GetMapping("/filterconges")
    public String getFilteredConges(@RequestParam(required = false) EtatConge etat, @SessionAttribute("userId") Long userId, Model model) {
        List<Conge> conges;
        Employee employee = employeeRepository.findById(userId).orElse(null);
        model.addAttribute("user", employee);

        if (etat != null) {
            conges = congeRepository.filterByEtat(etat);
        } else {
            conges = congeRepository.findByEmploye_Id(employee.getId());
        }

        model.addAttribute("conges", conges);
        return "employeeDashboard";
    }

    @GetMapping("/cancel/{id}")
    public String cancelConge(@PathVariable Long id, @SessionAttribute("userId") Long userId, Model model) {
        Conge conge = congeRepository.findById(id).orElse(null);
        if (conge == null || !conge.getEmploye().getId().equals(userId) || !conge.getEtat().equals(EtatConge.SOLLICITE)) {
            model.addAttribute("message", "Invalid request to cancel leave.");
            model.addAttribute("messageType", "error");
            return "redirect:/conges/employee/home";
        }

        conge.setEtat(EtatConge.ANNULE);
        congeRepository.save(conge);

        model.addAttribute("message", "Leave request has been cancelled successfully.");
        model.addAttribute("messageType", "success");
        return "redirect:/conges/employee/home";
    }

    @GetMapping("/demande")
    public String showDemandeCongeForm(Model model) {
        model.addAttribute("formConge", new CongeForm());
        return "demandeConge";
    }

    @PostMapping("/demande")
    public String demandeConge(@Valid @ModelAttribute("formConge") CongeForm congeForm,@SessionAttribute("userId") Long userId, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
        	model.addAttribute("messageType", "error");
            return "demandeConge";
        }

        // Assuming the current logged-in user is available in the session
        // You might need to replace this with actual user retrieval logic
        Employee employee = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", employee);
        if (employee != null) {
            LocalDate today = LocalDate.now();

            // Convert java.sql.Date to java.util.Date
            java.util.Date employmentDateUtil = new java.util.Date(employee.getDateEmbauchement().getTime());
            LocalDate employmentDate = employmentDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long employmentDuration = ChronoUnit.DAYS.between(employmentDate, today);

            if (employmentDuration < 365) {
                model.addAttribute("message", "L'employé doit avoir travaillé pendant au moins un an pour pouvoir demander un congé.");
                model.addAttribute("messageType", "error");
                return "demandeConge";
            }

            // Convert java.sql.Date to java.util.Date for start and end dates
            java.util.Date startDateUtil = new java.util.Date(congeForm.getDateDebut().getTime());
            java.util.Date endDateUtil = new java.util.Date(congeForm.getDateFin().getTime());

            LocalDate startDate = startDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = endDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            long requestedDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

            List<Conge> conges = congeRepository.findByEmploye_Id(employee.getId());
            long totalLeaveDays = conges.stream()
                                        .filter(conge -> conge.getEtat() != EtatConge.REFUSE)
                                        .mapToLong(conge -> {
                                            // Convert java.sql.Date to java.util.Date for each conge
                                            java.util.Date congeStartDateUtil = new java.util.Date(conge.getDateDebut().getTime());
                                            java.util.Date congeEndDateUtil = new java.util.Date(conge.getDateFin().getTime());
                                            LocalDate congeStartDate = congeStartDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                            LocalDate congeEndDate = congeEndDateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                            return ChronoUnit.DAYS.between(congeStartDate, congeEndDate) + 1;
                                        }).sum();

            if (totalLeaveDays + requestedDays > 30) {
                model.addAttribute("message", "L'employé ne peut pas dépasser 30 jours de congé au total.");
                model.addAttribute("messageType", "error");
                return "demandeConge";
            }

            Conge conge = new Conge();
            
            conge.setDescription(congeForm.getDescription());
            conge.setDateDebut(congeForm.getDateDebut());
            conge.setDateFin(congeForm.getDateFin());
            conge.setEmploye(employee);
            conge.setEtat(EtatConge.SOLLICITE);
            congeRepository.save(conge);
            

            model.addAttribute("message", "La demande de congé a été créée avec succès.");
            model.addAttribute("messageType", "success");
        } else {
            return "redirect:/login"; // Redirigez vers la page de connexion si l'employé est introuvable
        }

        return "demandeConge";
    }


    @GetMapping("/employee/home")
    public String employeeHome(@SessionAttribute("userId") Long userId, Model model) {
        Employee employee = employeeRepository.findById(userId).orElse(null);
        if (employee != null) {
            List<Conge> conges = congeRepository.findByEmploye_Id(employee.getId());
            model.addAttribute("conges", conges);
            model.addAttribute("user", employee);
            return "employeeDashboard";
        }
        return "redirect:/login";
    }
}
