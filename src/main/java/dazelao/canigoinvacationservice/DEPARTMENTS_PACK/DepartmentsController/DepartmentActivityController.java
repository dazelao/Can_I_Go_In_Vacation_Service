package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsController;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department-activity")
@Tag(name = "Создание связок между отделами и активностями", description ="Работа с отделами и активностями")
public class DepartmentActivityController {

    private final DepartmentActivityService departmentActivityService;

    @Autowired
    public DepartmentActivityController(DepartmentActivityService departmentActivityService) {
        this.departmentActivityService = departmentActivityService;
    }

    @PostMapping("/{departmentId}/add-activity/{activityId}")
    @Operation(summary = "Создание связки между отделами и их активностями")
    public ResponseEntity<String> addActivityToDepartment(@PathVariable int departmentId, @PathVariable int activityId) {
        departmentActivityService.addActivityToDepartment(departmentId, activityId);
        return ResponseEntity.ok("Activity added to department successfully.");
    }

    @GetMapping("/{departmentId}/activities")
    @Operation(summary = "Получение активностей которые есть на отделе")
    public ResponseEntity<List<Activity>> getActivitiesByDepartmentId(@PathVariable int departmentId) {
        List<Activity> activities = departmentActivityService.getActivitiesByDepartmentId(departmentId);
        return ResponseEntity.ok(activities);
    }

}