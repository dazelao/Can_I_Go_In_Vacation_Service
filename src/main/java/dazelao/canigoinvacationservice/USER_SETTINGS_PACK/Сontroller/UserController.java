package dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Сontroller;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Service.UserService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DepartmentActivityService departmentActivityService;


    @Autowired
    public UserController(UserService userService, DepartmentActivityService departmentActivityService) {
        this.userService = userService;
        this.departmentActivityService = departmentActivityService;
    }

    @PostMapping
    public ResponseEntity<BaseUser> createUser(@RequestBody BaseUser baseUser) {
        BaseUser createdUser = userService.saveUser(baseUser);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<BaseUser>> getAllUsers() {
        List<BaseUser> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseUser> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}/assign-department-activity/{departmentActivityId}")
    public ResponseEntity<BaseUser> assignDepartmentActivityToUser(@PathVariable Long userId, @PathVariable int departmentActivityId) {
        BaseUser user = userService.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found."));

        DepartmentActivity departmentActivity = departmentActivityService.getDepartmentActivityById(departmentActivityId);

        BaseUser updatedUser = userService.assignDepartmentActivityToUser(user, departmentActivity);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/department-activity/{departmentActivityId}")
    public ResponseEntity<List<BaseUser>> getUsersByDepartmentActivityId(@PathVariable int departmentActivityId) {
        List<BaseUser> users = userService.getUsersByDepartmentActivityId(departmentActivityId);
        return ResponseEntity.ok(users);
    }

   }