package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationService;


import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationParam.VacationParam;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationRepository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class VacationService {
    private final VacationRepository vacationRepository;

    @Autowired
    public VacationService(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }
    public Optional<VacationParam> getLastActivityVacationCount(Activity activity) {
        return vacationRepository.findFirstByActivityOrderByUpdatedAtDesc(activity);
    }

    public Optional<VacationParam> getLastDepartmentVacationCount(Department department) {
        return vacationRepository.findFirstByDepartmentOrderByUpdatedAtDesc(department);
    }

    public VacationParam saveActivityVacationCount(Activity activity, int activityVacationCount) {
        VacationParam existingRecord = vacationRepository.findByActivity(activity);
        if (existingRecord != null) {
            existingRecord.setActivityVacationCount(activityVacationCount);
            return vacationRepository.save(existingRecord);
        } else {
            VacationParam vacationParam = new VacationParam(activity, activityVacationCount);
            return vacationRepository.save(vacationParam);
        }
    }

    public VacationParam saveDepartmentVacationCount(Department department, int departmentVacationCount) {
        VacationParam existingRecord = vacationRepository.findByDepartment(department);
        if (existingRecord != null) {
            existingRecord.setDepartmentVacationCount(departmentVacationCount);
            return vacationRepository.save(existingRecord);
        } else {
            VacationParam vacationParam = new VacationParam(department, departmentVacationCount);
            return vacationRepository.save(vacationParam);
        }
    }



}
