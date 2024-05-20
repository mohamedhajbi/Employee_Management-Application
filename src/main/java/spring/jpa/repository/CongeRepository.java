package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.jpa.model.Conge;

import java.util.*;

@Repository
public interface CongeRepository extends JpaRepository<Conge, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
    List<Conge> findByEmployeId(Long employeId);

    List<Conge> findByEtatAndEmployeId(String etat, Long employeId);

    List<Conge> findByEtat(String etat);

    List<Conge> findByEmployeIdAndDateDebutBetween(Long employeId, Date startDate, Date endDate);

    List<Conge> findByDateDebutBetween(Date startDate, Date endDate);
}
