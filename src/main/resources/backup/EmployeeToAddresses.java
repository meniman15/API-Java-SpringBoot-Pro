package backup;

import javax.persistence.*;

@Entity
public class EmployeeToAddresses {

    @EmbeddedId
    EmployeeToAddressesId employeeToAddressesId;

    public EmployeeToAddressesId getEmployeeToAddressesId() {
        return employeeToAddressesId;
    }

    public void setEmployeeToAddressesId(EmployeeToAddressesId employeeToAddressesId) {
        this.employeeToAddressesId = employeeToAddressesId;
    }
}
