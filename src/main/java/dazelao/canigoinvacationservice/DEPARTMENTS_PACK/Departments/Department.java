package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String departmentName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "custom_department_activity",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<Activity> activities = new HashSet<>();

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department() {
    }
}
