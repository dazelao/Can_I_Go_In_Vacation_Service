package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DepartmentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

}