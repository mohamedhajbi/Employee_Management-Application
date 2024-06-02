package spring.jpa.repository;

import spring.jpa.model.Employee;
import spring.jpa.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Employee, Long> {

	User findByLoginAndPassword(String login, String password);

	Employee findByNom(String username);



}
