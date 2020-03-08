package poalim.project.repository;

import org.springframework.data.repository.CrudRepository;
import poalim.project.model.Employee;

public interface EmployeeRepo extends CrudRepository<Employee,Integer> {
}
