package poalim.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "children")
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int childId;

    @OneToOne
    GeneralDetails details;

    @JsonIgnore
    @ManyToOne
    Employee parent;

    public int getChildId() {
        return childId;
    }

    public void setChildId(int id) {
        this.childId = id;
    }

    public GeneralDetails getDetails() {
        return details;
    }

    public void setDetails(GeneralDetails details) {
        this.details = details;
    }

    public Employee getParent() {
        return parent;
    }

    public void setParent(Employee parentId) {
        this.parent = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child child = (Child) o;
        return childId == child.childId &&
                Objects.equals(details, child.details) &&
                Objects.equals(parent, child.parent);
    }
}
