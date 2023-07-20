package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleController;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleRequest;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService.ScheduleService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Service.UserService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final UserService userService;
    @Autowired
    public ScheduleController(ScheduleService scheduleService, UserService userService) {
        this.scheduleService = scheduleService;
        this.userService = userService;
    }

    @PostMapping("/generate")
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
    public ResponseEntity<String> generateScheduleByDepartmentActivity(@RequestBody ScheduleRequest request) {
        int departmentActivityId = request.getDepartmentActivityId();
        YearMonth yearMonth = YearMonth.of(request.getYear(), request.getMonthEnum());

        scheduleService.generateScheduleForDepartmentActivity(yearMonth, request.getWorkPattern(), departmentActivityId);

        return ResponseEntity.ok("Schedule generated successfully for DepartmentActivity.");
    }

    @GetMapping("/users/count")
    public ResponseEntity<Integer> getUsersCountByStatusAndDate(@RequestParam ScheduleStatus status, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int count = scheduleService.getUsersCountByStatusAndDate(status, date);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/users/count/range")
    public ResponseEntity<Map<LocalDate, Integer>> getUsersCountByStatusAndDateRange(@RequestParam ScheduleStatus status,
                                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, Integer> countByDate = scheduleService.getUsersCountByStatusAndDateRange(status, startDate, endDate);
        return ResponseEntity.ok(countByDate);
    }

}




