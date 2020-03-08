package poalim.project.repository;

import org.springframework.data.repository.CrudRepository;
import poalim.project.model.Child;

public interface ChildRepo extends CrudRepository<Child,Integer> {
}
