package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.Schedule;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.WorkPattern;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleRepo.ScheduleRepository;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DepartmentActivityService departmentActivityService;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, DepartmentActivityService departmentActivityService) {
        this.scheduleRepository = scheduleRepository;
        this.departmentActivityService = departmentActivityService;
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void generateScheduleForMonth(BaseUser user, YearMonth yearMonth, String workPattern) {
        WorkPattern pattern = createWorkPattern(workPattern);

        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        while (!startDate.isAfter(endDate)) {
            ScheduleStatus status = pattern.getStatusForDay(startDate.getDayOfMonth());

            Schedule schedule = new Schedule(startDate, status);
            schedule.setUser(user);
            saveSchedule(schedule);

            startDate = startDate.plusDays(1);
        }
    }

    public void generateScheduleForDepartmentActivity(YearMonth yearMonth, String workPattern, int departmentActivityId) {
        List<BaseUser> users = departmentActivityService.getUsersByDepartmentActivityId(departmentActivityId);
        for (BaseUser user : users) {
            generateScheduleForMonth(user, yearMonth, workPattern);
        }
    }

    private WorkPattern createWorkPattern(String workPattern) {
        List<ScheduleStatus> workDays = new ArrayList<>();
        for (char c : workPattern.toCharArray()) {
            if (c == 'Р') {
                workDays.add(ScheduleStatus.WORKING);
            } else if (c == 'В') {
                workDays.add(ScheduleStatus.DAY_OFF);
            } else if (c == 'O') {
                workDays.add(ScheduleStatus.OFF);
            }
        }
        return new WorkPattern(workDays);
    }
}

