package poalim.project.repository;

import org.springframework.data.repository.CrudRepository;
import poalim.project.model.Spouse;

public interface SpouseRepo extends CrudRepository<Spouse,Integer> {
}
