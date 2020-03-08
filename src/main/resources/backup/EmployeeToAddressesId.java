package backup;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeeToAddressesId  implements Serializable {

    int employeeId;

    int addressId;

    public EmployeeToAddressesId() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int personId) {
        this.employeeId = personId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeToAddressesId that = (EmployeeToAddressesId) o;
        return employeeId == that.employeeId &&
                addressId == that.addressId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, addressId);
    }
}
