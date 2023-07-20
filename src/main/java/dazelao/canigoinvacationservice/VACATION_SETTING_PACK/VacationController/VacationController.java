package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationController;


import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.ActivityService;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentService;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationParam.VacationParam;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationService.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/vacation")
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
    public ResponseEntity<VacationParam> setActivityVacationCount(@PathVariable Integer activityId, @PathVariable Integer count) {
        Optional<Activity> activity = activityService.getActivityById(activityId);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveActivityVacationCount(activity.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

    @PostMapping("/department/{departmentId}/count/{count}")
    public ResponseEntity<VacationParam> setDepartmentVacationCount(@PathVariable Integer departmentId, @PathVariable Integer count) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveDepartmentVacationCount(department.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

    @GetMapping("/activity/{activityId}/count")
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
    public ResponseEntity<VacationParam> updateActivityVacationCount(@PathVariable Integer activityId, @PathVariable Integer count) {
        Optional<Activity> activity = activityService.getActivityById(activityId);
        if (activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveActivityVacationCount(activity.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

    @PutMapping("/department/{departmentId}/count/{count}")
    public ResponseEntity<VacationParam> updateDepartmentVacationCount(@PathVariable Integer departmentId, @PathVariable Integer count) {
        Optional<Department> department = departmentService.getDepartmentById(departmentId);
        if (department.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        VacationParam vacationParam = vacationService.saveDepartmentVacationCount(department.get(), count);
        return ResponseEntity.ok(vacationParam);
    }

}