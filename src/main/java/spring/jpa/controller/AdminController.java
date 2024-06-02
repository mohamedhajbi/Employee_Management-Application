package spring.jpa.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import spring.jpa.enumeration.EtatConge;
import spring.jpa.model.Conge;
import spring.jpa.repository.CongeRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CongeRepository congeRepository;

    @GetMapping("/conges")
    public String listAllConges(Model model) {
        List<Conge> congeAll = congeRepository.findAllByOrderByDateDebutDesc();
        model.addAttribute("conges", congeAll);
        return "adminConges";
    }

    @GetMapping("/conges/filter")
    public String filterConges(@RequestParam(required = false) Long employeId,
                               @RequestParam(required = false) EtatConge etat,
                               @RequestParam(required = false) Integer year,
                               Model model) {
        List<Conge> conges = congeRepository.filterConges(employeId, etat, year);
        model.addAttribute("conges", conges);
        return "adminConges";
    }

    @PostMapping("/conges/validate/{id}")
    public String validateConge(@PathVariable Long id, Model model) {
        Conge conge = congeRepository.findById(id).orElse(null);
        if (conge != null && conge.getEtat() == EtatConge.SOLLICITE) {
            conge.setEtat(EtatConge.VALIDE);
            congeRepository.save(conge);
        }
        return "redirect:/admin/conges";
    }

    @PostMapping("/conges/refuse/{id}")
    public String refuseConge(@PathVariable Long id, Model model) {
        Conge conge = congeRepository.findById(id).orElse(null);
        if (conge != null && (conge.getEtat() == EtatConge.SOLLICITE || conge.getEtat() == EtatConge.VALIDE)) {
            conge.setEtat(EtatConge.REFUSE);
            congeRepository.save(conge);
        }
        return "redirect:/admin/conges";
    }

    @PostMapping("/conges/stop/{id}")
    public String stopConge(@PathVariable Long id, Model model) {
        Conge conge = congeRepository.findById(id).orElse(null);
        if (conge != null && conge.getEtat() == EtatConge.EN_COURS) {
            conge.setEtat(EtatConge.ARRETE);
            conge.setDateFin(new Date());
            congeRepository.save(conge);
        }
        return "redirect:/admin/conges";
    }
}
