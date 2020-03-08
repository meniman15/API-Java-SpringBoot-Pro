package poalim.project.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="spouses")
public class Spouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int spouseId;

    @OneToOne
    GeneralDetails details;

    public int getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(int id) {
        this.spouseId = id;
    }

    public GeneralDetails getDetails() {
        return details;
    }

    public void setDetails(GeneralDetails details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spouse spouse = (Spouse) o;
        return spouseId == spouse.spouseId &&
                Objects.equals(details, spouse.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spouseId, details);
    }
}
