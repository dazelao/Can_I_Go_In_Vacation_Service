package dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Service;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.DepartmentActivity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsRepository.DepartmentActivityRepository;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.UserRepository.UserRepository;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DepartmentActivityRepository departmentActivityRepo;

    @Autowired
    public UserService(UserRepository userRepository, DepartmentActivityRepository departmentActivityRepo) {
        this.userRepository = userRepository;
        this.departmentActivityRepo = departmentActivityRepo;
    }

    public BaseUser saveUser(BaseUser baseUser) {
        return userRepository.save(baseUser);
    }

    public Optional<BaseUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<BaseUser> getAllUsers() {
        return userRepository.findAll();
    }

    public BaseUser assignDepartmentActivityToUser(BaseUser user, DepartmentActivity departmentActivity) {
        DepartmentActivity savedDepartmentActivity = departmentActivityRepo.save(departmentActivity);
        user.setDepartmentActivity(savedDepartmentActivity);
        return userRepository.save(user);
    }

    public List<BaseUser> getUsersByDepartmentActivityId(int departmentActivityId) {
        return userRepository.findByDepartmentActivityId(departmentActivityId);
    }

    public BaseUser updateUser(BaseUser updatedUser) {
        // Make sure the user exists in the database before updating
        if (userRepository.existsById(updatedUser.getId())) {
            return userRepository.save(updatedUser);
        } else {
            throw new EntityNotFoundException("User with id " + updatedUser.getId() + " not found.");
        }
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}

