package dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.Schedule;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_activity")
    private DepartmentActivity departmentActivity;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();


    public BaseUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public BaseUser() {

    }
}

