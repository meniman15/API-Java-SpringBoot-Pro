package poalim.project.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="employees")
public class Employee extends RepresentationModel<Employee> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int employeeId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    GeneralDetails details;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    Spouse spouse;

    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Child> children;

    @OneToMany(mappedBy = "employee",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Set<Address> addresses;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int empId) {
        this.employeeId = empId;
    }

    public GeneralDetails getDetails() {
        return details;
    }

    public void setDetails(GeneralDetails details) {
        this.details = details;
    }

    public Spouse getSpouse() {
        return spouse;
    }

    public void setSpouse(Spouse spouse) {
        this.spouse = spouse;
    }

    public Set<Child> getChildren() {
        return children;
    }

    public void setChildren(Set<Child> children) {
        this.children = children;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId &&
                Objects.equals(details, employee.details) &&
                Objects.equals(spouse, employee.spouse) &&
                Objects.equals(children, employee.children) &&
                Objects.equals(addresses, employee.addresses);
    }
}

