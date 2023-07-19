package dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import jakarta.persistence.*;
import lombok.Data;


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


    public BaseUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public BaseUser() {

    }
}

