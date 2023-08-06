package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.ActivityRepository;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.DepartmentActivityRepository;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.DepartmentRepo;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.UserRepository.UserRepository;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentActivityService {

    private final DepartmentRepo departmentRepo;
    private final ActivityRepository activityRepo;
    private final DepartmentActivityRepository departmentActivityRepo;
    private final UserRepository userRepository;

    @Autowired
    public DepartmentActivityService(DepartmentRepo departmentRepo, ActivityRepository activityRepo, DepartmentActivityRepository departmentActivityRepo, UserRepository userRepository) {
        this.departmentRepo = departmentRepo;
        this.activityRepo = activityRepo;
        this.departmentActivityRepo = departmentActivityRepo;
        this.userRepository = userRepository;
    }

    public void addActivityToDepartment(int departmentId, int activityId) {
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + departmentId + " not found."));

        Activity activity = activityRepo.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with id " + activityId + " not found."));

        DepartmentActivity departmentActivity = new DepartmentActivity();
        departmentActivity.setDepartment(department);
        departmentActivity.setActivity(activity);

        departmentActivityRepo.save(departmentActivity);
    }

    public List<Activity> getActivitiesByDepartmentId(int departmentId) {
        Department department = departmentRepo.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("Department with id " + departmentId + " not found."));

        List<DepartmentActivity> departmentActivities = departmentActivityRepo.findByDepartmentId(departmentId);
        return departmentActivities.stream()
                .map(DepartmentActivity::getActivity)
                .collect(Collectors.toList());
    }

    public DepartmentActivity getDepartmentActivityById(int departmentActivityId) {
        return departmentActivityRepo.findById(departmentActivityId)
                .orElseThrow(() -> new EntityNotFoundException("DepartmentActivity with id " + departmentActivityId + " not found."));
    }

    public List<BaseUser> getUsersByDepartmentActivityId(int departmentActivityId) {
        return userRepository.findByDepartmentActivityId(departmentActivityId);
    }

    public void updateDepartmentActivity(int departmentActivityId, int newActivityId) {
        DepartmentActivity departmentActivity = departmentActivityRepo.findById(departmentActivityId)
                .orElseThrow(() -> new EntityNotFoundException("DepartmentActivity with id " + departmentActivityId + " not found."));

        Activity newActivity = activityRepo.findById(newActivityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity with id " + newActivityId + " not found."));

        departmentActivity.setActivity(newActivity);

        departmentActivityRepo.save(departmentActivity);
    }

    public void deleteDepartmentActivity(int departmentActivityId) {
        DepartmentActivity departmentActivity = departmentActivityRepo.findById(departmentActivityId)
                .orElseThrow(() -> new EntityNotFoundException("DepartmentActivity with id " + departmentActivityId + " not found."));

        departmentActivityRepo.delete(departmentActivity);
    }

}
