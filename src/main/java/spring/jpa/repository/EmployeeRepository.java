package spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.jpa.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByLogin(String login);
}
