package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationRepository;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationParam.VacationParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VacationRepository extends JpaRepository<VacationParam, Long>{
    
    Optional<VacationParam> findFirstByActivityOrderByUpdatedAtDesc(Activity activity);
    Optional<VacationParam> findFirstByDepartmentOrderByUpdatedAtDesc(Department department);

    VacationParam findByActivity(Activity activity);

    VacationParam findByDepartment(Department department);
}
