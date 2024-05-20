package spring.jpa.repository;

import java.util.Date;
import java.util.List;
import spring.jpa.model.Conge;
import spring.jpa.model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Employee, Long> {
    //List<Conge> consulterTousConges();
   // List<Conge> filtrerConges(String employe, String etat, int annee);
  //  void validerConge(Long congeId);
   // void refuserConge(Long congeId);
  //  void arreterConge(Long congeId, Date dateRupture);
}
