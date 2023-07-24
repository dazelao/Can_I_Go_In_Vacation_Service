package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleRepo;

import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.Schedule;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    int countByStatusAndDate(ScheduleStatus status, LocalDate date);


    @Query("SELECT s.date, COUNT(s) " +
            "FROM Schedule s " +
            "WHERE s.status = :status AND s.date BETWEEN :startDate AND :endDate " +
            "GROUP BY s.date")
    List<Object[]> countByStatusAndDateBetween(@Param("status") ScheduleStatus status, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    List<Schedule> findByDateBetween(LocalDate of, LocalDate of1);
}

