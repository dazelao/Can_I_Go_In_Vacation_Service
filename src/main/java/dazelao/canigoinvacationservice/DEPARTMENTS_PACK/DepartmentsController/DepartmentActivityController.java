package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsController;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department-activity")
public class DepartmentActivityController {

    private final DepartmentActivityService departmentActivityService;

    @Autowired
    public DepartmentActivityController(DepartmentActivityService departmentActivityService) {
        this.departmentActivityService = departmentActivityService;
    }

    @PostMapping("/{departmentId}/add-activity/{activityId}")
    public ResponseEntity<String> addActivityToDepartment(@PathVariable int departmentId, @PathVariable int activityId) {
        departmentActivityService.addActivityToDepartment(departmentId, activityId);
        return ResponseEntity.ok("Activity added to department successfully.");
    }

    @GetMapping("/{departmentId}/activities")
    public ResponseEntity<List<Activity>> getActivitiesByDepartmentId(@PathVariable int departmentId) {
        List<Activity> activities = departmentActivityService.getActivitiesByDepartmentId(departmentId);
        return ResponseEntity.ok(activities);
    }

}