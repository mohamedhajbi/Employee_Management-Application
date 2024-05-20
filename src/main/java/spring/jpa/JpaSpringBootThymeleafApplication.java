package spring.jpa;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import spring.jpa.enumeration.EtatConge;
import spring.jpa.model.Conge;
import spring.jpa.model.Employee;
import spring.jpa.repository.CongeRepository;
import spring.jpa.repository.UserRepository; // Import UserRepository instead of EmployeRepository

@SpringBootApplication
public class JpaSpringBootThymeleafApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JpaSpringBootThymeleafApplication.class, args);

        UserRepository userRepository = context.getBean(UserRepository.class); // Use UserRepository instead of EmployeRepository
        CongeRepository congeRepository = context.getBean(CongeRepository.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdf.parse("2022-04-15");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Employee employee1 = new Employee();
        employee1.setCode("E001");
        employee1.setNom("med");
        employee1.setPrenom("Jean");
        employee1.setDateEmbauchement(date1);
        employee1.setLogin("jdupont");
        employee1.setPassword("password1");
        userRepository.save(employee1); // Use userRepository.save(employee1) instead of employeRepository.save(employee1)

        Employee employee2 = new Employee();
        employee2.setCode("E0aza01");
        employee2.setNom("parker");
        employee2.setPrenom("azaz");
        employee2.setDateEmbauchement(date1);
        employee2.setLogin("aza");
        employee2.setPassword("azaza66");
        userRepository.save(employee2);

        Employee employee3 = new Employee();
        employee3.setCode("002a");
        employee3.setNom("saif");
        employee3.setPrenom("parker");
        employee3.setDateEmbauchement(date1);
        employee3.setLogin("paekerparker");
        employee3.setPassword("dawed665");
        userRepository.save(employee3);


        Conge conge1 = new Conge();
        conge1.setDescription("Vacation");
        conge1.setDateDebut(date1);
        conge1.setDateFin(date1);
        conge1.setEmploye(employee1);
        conge1.setEtat(EtatConge.SOLLICITE);
        congeRepository.save(conge1);
    }

}
