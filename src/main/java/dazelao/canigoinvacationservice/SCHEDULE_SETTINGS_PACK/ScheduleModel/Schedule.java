package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel;

import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BaseUser user;

    public Schedule(LocalDate date, ScheduleStatus status) {
        this.date = date;
        this.status = status;
        if (status != ScheduleStatus.WORKING) {
            this.startTime = LocalTime.of(0, 0);
            this.endTime = LocalTime.of(0, 0);
        } else {
            this.startTime = LocalTime.of(8, 30);
            this.endTime = LocalTime.of(20, 30);
        }
    }

    public Schedule() {

    }
}