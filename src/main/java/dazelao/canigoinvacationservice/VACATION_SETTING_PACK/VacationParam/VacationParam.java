package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationParam;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class VacationParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    private int activityVacationCount;

    @Column
    private int departmentVacationCount;

    public VacationParam(Activity activity, int activityVacationCount) {
        this.activity = activity;
        this.activityVacationCount = activityVacationCount;
    }

    public VacationParam(Department department, int departmentVacationCount) {
        this.department = department;
        this.departmentVacationCount = departmentVacationCount;
    }

    public VacationParam() {
    }

}

