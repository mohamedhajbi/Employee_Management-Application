package spring.jpa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import spring.jpa.enumeration.EtatConge;
import spring.jpa.model.Conge;
import spring.jpa.model.Employee;
import spring.jpa.repository.CongeRepository;
import spring.jpa.repository.UserRepository;

@SpringBootApplication
public class JpaSpringBootThymeleafApplication {

    static UserRepository userRepository;
    static CongeRepository congeRepository;

    public static void main(String[] args) throws ParseException {
        ApplicationContext context = SpringApplication.run(JpaSpringBootThymeleafApplication.class, args);
        userRepository = context.getBean(UserRepository.class);
        congeRepository = context.getBean(CongeRepository.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2022-04-05");
        Date date2 = sdf.parse("2022-03-29");
        Date date3 = sdf.parse("2022-02-01");
        Date date4 = sdf.parse("2022-02-04");
        Date date5 = sdf.parse("2024-02-14");
        Date date6 = sdf.parse("2024-02-26");
        


        Employee employee1 = new Employee();
        employee1.setCode("E0701");
        employee1.setNom("med");
        employee1.setPrenom("Jean");
        employee1.setDateEmbauchement(date1);
        employee1.setLogin("jdupont");
        employee1.setPassword("password1");
        userRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setCode("E0aza01");
        employee2.setNom("parker");
        employee2.setPrenom("azaz");
        employee2.setDateEmbauchement(date1);
        employee2.setLogin("aza");
        employee2.setPassword("azaza66");
        userRepository.save(employee2);
        
        Employee employee6 = new Employee();
        employee6.setCode("ABCDEF");
        employee6.setNom("Mohammed");
        employee6.setPrenom("Bellaaj");
        employee6.setDateEmbauchement(date6);
        employee6.setLogin("parker");
        employee6.setPassword("parker");
        userRepository.save(employee6);
        
         

        Employee employee3 = new Employee();
        employee3.setCode("002a");
        employee3.setNom("saif");
        employee3.setPrenom("parker");
        employee3.setDateEmbauchement(date5);
        employee3.setLogin("paekerparker"); 
        employee3.setPassword("dawed665");
        userRepository.save(employee3);
        

        Conge conge1 = new Conge();
        conge1.setDescription("Vacation");
        conge1.setDateDebut(date2);
        conge1.setDateFin(date1);
        conge1.setEmploye(employee1);
        conge1.setEtat(EtatConge.SOLLICITE);
        congeRepository.save(conge1);

        

        Conge conge3 = new Conge();
        conge3.setDescription("Maternite");
        conge3.setDateDebut(date3);
        conge3.setDateFin(date4);
        conge3.setEmploye(employee2);
        conge3.setEtat(EtatConge.FINI);
        congeRepository.save(conge3);
        
        Conge conge2 = new Conge();
        conge2.setDescription("Martial");
        conge2.setDateDebut(date5);
        conge2.setDateFin(date6);
        conge2.setEmploye(employee2);
        conge2.setEtat(EtatConge.SOLLICITE);
        congeRepository.save(conge2);


      
        
       
        
      

        // Afficher la liste de tous les congés
        System.out.println("***************************************");
        List<Conge> congeAll = congeRepository.findAll();
        System.out.println("Afficher la liste de tous les congés");
        System.out.println("***************************************");
        for (Conge c : congeAll) {
            System.out.println(c);
            System.out.println();
        }

        // Consulter l’historique de ces congés
        System.out.println("***************************************");
        List<Conge> lg = congeRepository.findByEmploye_Id(employee2.getId());
        System.out.println("***************************************");
        if (lg != null && !lg.isEmpty()) {
            System.out.println("Afficher l’historique des congés pour l'employé ayant id correspondant");
            System.out.println("");
            for (Conge c : lg) {
                System.out.println(c);
            }
        } else {
            System.out.println("employé n'a pas encore de congé...");
        }

        // Filtrer la liste des congés par état
        List<Conge> congeParEtat = congeRepository.findByEtatAndEmployeId(EtatConge.SOLLICITE, employee2.getId());
        if (congeParEtat != null && !congeParEtat.isEmpty()) {
            System.out.println("Filtrer la liste des congés par état pour l'employé ayant id correspondant");
            System.out.println("");
            for (Conge ce : congeParEtat) {
                System.out.println(ce);
            }
        } else {
            System.out.println("employé n'a de congé sollicité...");
        }

        // Filtrer la liste des congés par année
        List<Conge> congeParAnnee = congeRepository.findByEmployeIdAndDateDebutBetween(employee2.getId(), sdf.parse("2022-02-01"), sdf.parse("2022-04-16"));
        if (congeParAnnee != null && !congeParAnnee.isEmpty()) {
            System.out.println("Filtrer la liste des congés par annee pour l'employé ayant id correspondant");
            System.out.println("");
            for (Conge ca : congeParAnnee) {
                System.out.println(ca);
            }
        } else {
            System.out.println("Pas de congé correspond à ces dates ...");
        }


    }
    
    public static String annulerConge(Long congeId, Long employeId) {
        // Utilisez orElseThrow pour obtenir le Conge ou lever une exception si non trouvé
        Conge conge = congeRepository.findById(congeId).orElseThrow();

        // Vérifie si le congé appartient à l'employé
        if (!conge.getEmploye().getId().equals(employeId)) {
            return "Vous n'êtes pas autorisé à annuler ce congé";
        }

        // Vérifie l'état du congé
        if (!conge.getEtat().equals(EtatConge.SOLLICITE)) {
            return "Le congé ne peut être annulé que s'il est dans l'état 'SOLLICITE'";
        }

        conge.setEtat(EtatConge.ANNULE);
        congeRepository.save(conge);
        return "Le congé a été annulé avec succès";
    }
}
