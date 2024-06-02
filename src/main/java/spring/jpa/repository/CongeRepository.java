package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.jpa.enumeration.EtatConge;
import spring.jpa.model.Conge;
import spring.jpa.model.Employee;

import java.util.*;

@Repository
public interface CongeRepository extends JpaRepository<Conge, Long> {
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
    List<Conge> findAllByOrderByDateDebutDesc();
	List<Conge> findByEmploye(Employee employe);
    List<Conge> findByEmploye_Id(Long employeId);
    List<Conge> findAll();
    List<Conge> findByEtatAndEmployeId(EtatConge etat, Long employeId);


    List<Conge> findByEmployeIdAndDateDebutBetween(Long employeId, Date startDate, Date endDate);

    List<Conge> findByDateDebutBetween(Date startDate, Date endDate);
    @Query("SELECT c FROM Conge c WHERE (:employeId IS NULL OR c.employe.id = :employeId) AND (:etat IS NULL OR c.etat = :etat) AND (:year IS NULL OR FUNCTION('YEAR', c.dateDebut) = :year)")
    List<Conge> filterConges(Long employeId, EtatConge etat, Integer year);
    
   

    @Query("SELECT c FROM Conge c WHERE (:etat IS NULL OR c.etat = :etat)")
    List<Conge> filterByEtat(@Param("etat") EtatConge etat);

    List<Conge> findByEtat(EtatConge etat);
    
    

	
}
