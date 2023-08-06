package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleController;

import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleRequest;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService.ScheduleService;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService.ScheduleTotalTimeService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Service.UserService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;



@RestController
@RequestMapping("/api/schedule")
@Tag(name = "Расписание", description ="Работа с расписанием")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;

    private final ScheduleTotalTimeService scheduleTotalTimeService;
    @Autowired
    public ScheduleController(ScheduleService scheduleService, UserService userService, ScheduleTotalTimeService scheduleTotalTimeService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
        this.scheduleTotalTimeService = scheduleTotalTimeService;
    }

    @PostMapping("/generate")
    @Operation(summary = "Генерация расписания по его id")
    public ResponseEntity<String> generateSchedule(@RequestBody ScheduleRequest request) {
        Long userId = request.getUserId();
        Optional<BaseUser> optionalUser = userService.getUserById(userId);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        BaseUser user = optionalUser.get();
        YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonthEnum());

        scheduleService.generateScheduleForMonth(user, yearMonth, request.getWorkPattern());

        return ResponseEntity.ok("Schedule generated successfully.");
    }

    @PostMapping("/generateByDepartmentActivity")
    @Operation(summary = "Генерация расписания по активностям отдела")
    public ResponseEntity<String> generateScheduleByDepartmentActivity(@RequestBody ScheduleRequest request) {
        int departmentActivityId = request.getDepartmentActivityId();
        YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonthEnum());

        scheduleService.generateScheduleForDepartmentActivity(yearMonth, request.getWorkPattern(), departmentActivityId);

        return ResponseEntity.ok("Schedule generated successfully for DepartmentActivity.");
    }

    @GetMapping("/users/count")
    @Operation(summary = "Получение пользователей по их статусам на конкретную дату (2023-07-28 - пример даты)")
    public ResponseEntity<Integer> getUsersCountByStatusAndDate(@RequestParam ScheduleStatus status, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int count = scheduleService.getUsersCountByStatusAndDate(status, date);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/users/count/range")
    @Operation(summary = "Получаем нагрузку по периоду")
    public ResponseEntity<Map<LocalDate, Integer>> getUsersCountByStatusAndDateRange(@RequestParam ScheduleStatus status,
                                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, Integer> countByDate = scheduleService.getUsersCountByStatusAndDateRange(status, startDate, endDate);
        return ResponseEntity.ok(countByDate);
    }


    @PostMapping("/total-time")
    @Operation(summary = "Список нагрузки  " +
            "{\n" +
            "\"year\": 2023,\n" +
            "\"month\": \"JULY\"\n" +
            " }")
    public ResponseEntity<Map<Long, String>> getTotalTimeByMonth(@RequestBody ScheduleRequest scheduleRequest) throws ExecutionException, InterruptedException {
        Map<Long, String> totalTimeMap = scheduleTotalTimeService.getTotalTimeByUserIdAndMonth(scheduleRequest);
        return ResponseEntity.ok(totalTimeMap);
    }


}




