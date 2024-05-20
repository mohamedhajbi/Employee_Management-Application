package spring.jpa.model;


import java.util.Date;

import jakarta.persistence.*;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;

@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    @NotNull
    @Size(max = 50)
    private String code;

    @NotNull
    @Size(max = 50)
    private String nom;

    @NotNull
    @Size(max = 50)
    private String prenom;

    @NotNull
    @Past
    @Temporal(TemporalType.DATE)
    private Date dateEmbauchement;

    @Column(unique = true)
    @NotNull
    @Size(max = 50)
    private String login;

    @NotNull
    @Size(min = 6, max = 10)
    private String password;

    // Getters and setters with constraints
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateEmbauchement() {
        return dateEmbauchement;
    }

    public void setDateEmbauchement(Date dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
