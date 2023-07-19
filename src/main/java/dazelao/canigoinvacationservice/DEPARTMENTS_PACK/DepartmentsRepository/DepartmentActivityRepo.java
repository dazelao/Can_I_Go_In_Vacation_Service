package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentActivityRepo extends JpaRepository<DepartmentActivity, Integer> {
    List<DepartmentActivity> findByDepartmentId(int departmentId);
}

