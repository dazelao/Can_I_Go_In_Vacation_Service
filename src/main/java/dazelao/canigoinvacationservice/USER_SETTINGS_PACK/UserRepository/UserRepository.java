package dazelao.canigoinvacationservice.USER_SETTINGS_PACK.UserRepository;

import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<BaseUser, Long> {
    List<BaseUser> findByDepartmentActivityId(int departmentActivityId);
}