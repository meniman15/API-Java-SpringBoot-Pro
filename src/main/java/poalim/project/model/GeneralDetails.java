package poalim.project.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class GeneralDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int detailsId;

    String name;

    int age;

    public enum Gender {
        MALE,
        FEMALE
    }
    @Enumerated(EnumType.STRING)
    Gender gender;

    public int getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(int personId) {
        this.detailsId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralDetails details = (GeneralDetails) o;
        return detailsId == details.detailsId &&
                age == details.age &&
                Objects.equals(name, details.name) &&
                gender == details.gender;
    }
}
