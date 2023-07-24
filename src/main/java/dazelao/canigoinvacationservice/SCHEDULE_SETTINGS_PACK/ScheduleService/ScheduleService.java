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
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


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

//    public void generateScheduleForDepartmentActivity(YearMonth yearMonth, String workPattern, int departmentActivityId) {
//        List<BaseUser> users = departmentActivityService.getUsersByDepartmentActivityId(departmentActivityId);
//        for (BaseUser user : users) {
//            generateScheduleForMonth(user, yearMonth, workPattern);
//        }
//    }
public void generateScheduleForDepartmentActivity(YearMonth yearMonth, String workPattern, int departmentActivityId) {
    long startTime = System.currentTimeMillis();
    List<BaseUser> users = departmentActivityService.getUsersByDepartmentActivityId(departmentActivityId);


    int numThreads = Runtime.getRuntime().availableProcessors();

    ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

    for (BaseUser user : users) {
        Runnable task = () -> generateScheduleForMonth(user, yearMonth, workPattern);
        executorService.submit(task);
    }
    executorService.shutdown();
    try {
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("Время выполнения метода generateScheduleForDepartmentActivity: " + (endTime - startTime) + " мс");
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

    public int getUsersCountByStatusAndDate(ScheduleStatus status, LocalDate date) {
        return scheduleRepository.countByStatusAndDate(status, date);
    }

    public Map<LocalDate, Integer> getUsersCountByStatusAndDateRange(ScheduleStatus status, LocalDate startDate, LocalDate endDate) {
        List<Object[]> result = scheduleRepository.countByStatusAndDateBetween(status, startDate, endDate);
        Map<LocalDate, Integer> countByDate = new HashMap<>();
        for (Object[] row : result) {
            LocalDate date = (LocalDate) row[0];
            Long count = (Long) row[1];
            countByDate.put(date, count.intValue());
        }
        return countByDate;
    }


}



