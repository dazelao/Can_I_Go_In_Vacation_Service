package dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Сontroller;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Service.UserService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description ="Контроллер работы с пользователями")
public class UserController {
    private final UserService userService;
    private final DepartmentActivityService departmentActivityService;


    @Autowired
    public UserController(UserService userService, DepartmentActivityService departmentActivityService) {
        this.userService = userService;
        this.departmentActivityService = departmentActivityService;
    }

    @PostMapping
    @Operation(summary = "Создание пользователя")
    public ResponseEntity<BaseUser> createUser(@RequestBody BaseUser baseUser) {
        BaseUser createdUser = userService.saveUser(baseUser);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    @Operation(summary = "Получение списка пользователей")
    public ResponseEntity<List<BaseUser>> getAllUsers() {
        List<BaseUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение пользователя по id")
    public ResponseEntity<BaseUser> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/assign-department-activity/{departmentActivityId}")
    @Operation(summary = "Обновление связок отдела и активностей пользователя")
    public ResponseEntity<BaseUser> assignDepartmentActivityToUser(@PathVariable Long userId, @PathVariable int departmentActivityId) {
        BaseUser user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found."));

        DepartmentActivity departmentActivity = departmentActivityService.getDepartmentActivityById(departmentActivityId);

        BaseUser updatedUser = userService.assignDepartmentActivityToUser(user, departmentActivity);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/department-activity/{departmentActivityId}")
    @Operation(summary = "Получение списка пользователей по активностям отдела")
    public ResponseEntity<List<BaseUser>> getUsersByDepartmentActivityId(@PathVariable int departmentActivityId) {
        List<BaseUser> users = userService.getUsersByDepartmentActivityId(departmentActivityId);
        return ResponseEntity.ok(users);
    }

   }