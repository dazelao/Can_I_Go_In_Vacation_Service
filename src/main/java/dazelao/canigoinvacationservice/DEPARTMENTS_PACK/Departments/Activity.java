package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    public Activity(String name) {
        this.name = name;
    }

    public Activity() {

    }
}