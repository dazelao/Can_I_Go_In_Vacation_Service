package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel;

import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import lombok.Data;

import java.time.Month;


@Data
public class ScheduleRequest {

    private Long userId;
    private int year;
    private String month;
    private String workPattern;
    private int departmentActivityId;

    public Month getMonthEnum() {
        return Month.valueOf(month.toUpperCase());
    }

}

