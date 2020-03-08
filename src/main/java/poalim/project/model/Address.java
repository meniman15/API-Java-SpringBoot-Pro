package poalim.project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int addressId;

    String city;
    String street;
    int apartmentNumber;

    @JsonIgnore
    @ManyToOne
    Employee employee;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int number) {
        this.apartmentNumber = number;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee residentId) {
        this.employee = residentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId &&
                apartmentNumber == address.apartmentNumber &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(employee, address.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, city, street, apartmentNumber, employee);
    }
}
