package spring.jpa.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import spring.jpa.enumeration.EtatConge;
import spring.jpa.repository.CongeRepository;

@Entity
public class Employee extends User {
    @OneToMany(mappedBy = "employe")
    private List<Conge> conges = new ArrayList<>();

    public List<Conge> getConges() {
        return conges;
    }

    public void setConges(List<Conge> conges) {
        this.conges = conges;
    }

    
   



}
