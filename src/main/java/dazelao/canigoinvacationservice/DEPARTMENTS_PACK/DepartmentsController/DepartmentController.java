package dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsController;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Tag(name = "Отделы", description = "Работа с отделами")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @Operation(summary = "Генерация нового отдела")
    public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return ResponseEntity.ok(savedDepartment);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение данных по отделу по его id")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id) {
        return departmentService.getDepartmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Получение списка отделов")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartment();
        return ResponseEntity.ok(departments);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных по отделу по его id")
    public ResponseEntity<Department> updateDepartment(@PathVariable Integer id, @RequestBody Department updatedDepartment) {
        Department existingDepartment = departmentService.getDepartmentById(id)
                .orElse(null);

        if (existingDepartment == null) {
            return ResponseEntity.notFound().build();
        }

        existingDepartment.setDepartmentName(updatedDepartment.getDepartmentName());

        Department updatedDepartmentEntity = departmentService.updateDepartment(existingDepartment);
        return ResponseEntity.ok(updatedDepartmentEntity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление отдела по его id")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        Department existingDepartment = departmentService.getDepartmentById(id)
                .orElse(null);

        if (existingDepartment == null) {
            return ResponseEntity.notFound().build();
        }

        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }
}

