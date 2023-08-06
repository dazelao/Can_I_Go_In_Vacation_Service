package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Optional<Activity> getActivityById(Integer activityId) {
        return activityRepository.findById(activityId);
    }

    public Activity updateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivityById(Integer activityId) {
        activityRepository.deleteById(activityId);
    }
}

