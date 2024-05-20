package spring.jpa.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import spring.jpa.enumeration.EtatConge;
import jakarta.persistence.*;

@Entity
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String description;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @Temporal(TemporalType.DATE)
    private Date dateRupture;

    @Enumerated(EnumType.STRING)
    private EtatConge etat;
    
    @ManyToOne
    private Employee employe;

    // Getters and setters with constraints
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateRupture() {
        return dateRupture;
    }

    public void setDateRupture(Date dateRupture) {
        this.dateRupture = dateRupture;
    }

    public EtatConge getEtat() {
        return etat;
    }

    public void setEtat(EtatConge etat) {
        this.etat = etat;
    }

    public Employee getEmploye() {
        return employe;
    }

    public void setEmploye(Employee employe) {
        this.employe = employe;
    }
}
