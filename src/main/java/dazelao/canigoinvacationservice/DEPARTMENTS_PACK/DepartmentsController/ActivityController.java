package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsController;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/activities")
@Tag(name = "Создание активностей", description = "Работа с активностями отдела")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/add")
    @Operation(summary = "Генерация новой активности")
    public ResponseEntity<Activity> addActivity(@RequestBody Activity activity) {
        Activity savedActivity = activityService.saveActivity(activity);
        return ResponseEntity.ok(savedActivity);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Обновление активности по идентификатору")
    public ResponseEntity<Activity> updateActivity(
            @PathVariable Integer id,
            @RequestBody Activity updatedActivityRequest
    ) {
        Optional<Activity> optionalActivity = activityService.getActivityById(id);

        if (optionalActivity.isPresent()) {
            Activity existingActivity = optionalActivity.get();
            existingActivity.setName(updatedActivityRequest.getName());

            Activity updatedActivity = activityService.updateActivity(existingActivity);
            return ResponseEntity.ok(updatedActivity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удаление активности по идентификатору")
    public ResponseEntity<Void> deleteActivity(@PathVariable Integer id) {
        Optional<Activity> optionalActivity = activityService.getActivityById(id);

        if (optionalActivity.isPresent()) {
            activityService.deleteActivityById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
