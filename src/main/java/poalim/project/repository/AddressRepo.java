package poalim.project.repository;

import org.springframework.data.repository.CrudRepository;
import poalim.project.model.Address;

public interface AddressRepo  extends CrudRepository<Address,Integer> {
}
