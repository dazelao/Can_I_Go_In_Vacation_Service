package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService;


import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepo;

    @Autowired
    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }

    public Department saveDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public Optional<Department> getDepartmentById(Integer id) {
        return departmentRepo.findById(id);
    }

    public List<Department> getAllDepartment() {
        return departmentRepo.findAll();
    }


}
