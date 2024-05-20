package spring.jpa.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Employee extends User {
    @OneToMany(mappedBy = "employe")
    private List<Conge> conges;

	public List<Conge> getConges() {
		return conges;
	}

	public void setConges(List<Conge> conges) {
		this.conges = conges;
	}

}