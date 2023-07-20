package dazelao.canigoinvacationservice;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.DepartmentRepo;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveDepartment() {
        Department departmentToSave = new Department("IT");
        Department savedDepartment = new Department(1, "IT");

        when(departmentRepo.save(departmentToSave)).thenReturn(savedDepartment);

        Department result = departmentService.saveDepartment(departmentToSave);

        assertEquals(savedDepartment, result);
        verify(departmentRepo, times(1)).save(departmentToSave);
    }

    @Test
    void testGetDepartmentById() {
        int departmentId = 1;
        Department department = new Department(departmentId, "HR");

        when(departmentRepo.findById(departmentId)).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.getDepartmentById(departmentId);

        assertEquals(Optional.of(department), result);
        verify(departmentRepo, times(1)).findById(departmentId);
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "HR"));
        departments.add(new Department(2, "IT"));

        when(departmentRepo.findAll()).thenReturn(departments);

        List<Department> result = departmentService.getAllDepartment();

        assertEquals(departments, result);
        verify(departmentRepo, times(1)).findAll();
    }
}
