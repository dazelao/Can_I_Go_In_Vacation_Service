package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationController;


import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.ActivityService;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentService;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationParam.VacationParam;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationService.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vacation")
@Tag(name = "Отпуск конфигуратор", description ="работа с лимитами")
public class VacationController {
    private final VacationService vacationService;
    private final ActivityService activityService;
    private final DepartmentService departmentService;

    @Autowired
    public VacationController(VacationService vacationService, ActivityService activityService, DepartmentService departmentService) {
        this.vacationService = vacationService;
        this.activityService = activityService;
        this.departmentService = departmentService;
    }

    @PostMapping("/activity/{activityId}/count/{count}")
    @Operation(summary = "Установка квоты, по активности")
    public ResponseEntity<VacationParam> setActivityVacationCount(@PathVariable Integer activityId, @PathVariable Integer count) {
        Optional<Activity> activity = activityService.getActivityById(activityId);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveActivityVacationCount(activity.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

    @PostMapping("/department/{departmentId}/count/{count}")
    @Operation(summary = "Установка квоты, по отделу")
    public ResponseEntity<VacationParam> setDepartmentVacationCount(@PathVariable Integer departmentId, @PathVariable Integer count) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveDepartmentVacationCount(department.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

    @GetMapping("/activity/{activityId}/count")
    @Operation(summary = "Получение квоты, по активности")
    public ResponseEntity<Integer> getActivityVacationCount(@PathVariable Integer activityId) {
        Optional<Activity> activity = activityService.getActivityById(activityId);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<VacationParam> vacationParam = vacationService.getLastActivityVacationCount(activity.get());
        int count = vacationParam.map(VacationParam::getActivityVacationCount).orElse(0);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/department/{departmentId}/count")
    @Operation(summary = "Получение квоты, по департаменту")
    public ResponseEntity<Integer> getDepartmentVacationCount(@PathVariable Integer departmentId) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<VacationParam> vacationParam = vacationService.getLastDepartmentVacationCount(department.get());
        int count = vacationParam.map(VacationParam::getDepartmentVacationCount).orElse(0);
        return ResponseEntity.ok(count);
    }

    @PutMapping("/activity/{activityId}/count/{count}")
    @Operation(summary = "Обновление квоты, по активности")
    public ResponseEntity<VacationParam> updateActivityVacationCount(@PathVariable Integer activityId, @PathVariable Integer count) {
        Optional<Activity> activity = activityService.getActivityById(activityId);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveActivityVacationCount(activity.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

    @PutMapping("/department/{departmentId}/count/{count}")
    @Operation(summary = "Обновление квоты, по отделу")
    public ResponseEntity<VacationParam> updateDepartmentVacationCount(@PathVariable Integer departmentId, @PathVariable Integer count) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveDepartmentVacationCount(department.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

}