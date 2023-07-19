package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository;


import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
}
