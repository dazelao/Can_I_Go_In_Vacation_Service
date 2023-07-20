package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleRepo;

import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}

