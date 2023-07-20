package dazelao.canigoinvacationservice;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;

import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.DepartmentsService.DepartmentActivityService;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.Schedule;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleRepo.ScheduleRepository;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService.ScheduleService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private DepartmentActivityService departmentActivityService;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateScheduleForMonth_ValidData_ScheduleSavedForEachDay() {

        BaseUser user = new BaseUser();
        YearMonth yearMonth = YearMonth.of(2023, 7);
        String workPattern = "РРРРВОООР";

        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();


        when(departmentActivityService.getUsersByDepartmentActivityId(anyInt())).thenReturn(Arrays.asList(user));


        scheduleService.generateScheduleForDepartmentActivity(yearMonth, workPattern, 1);

        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            ScheduleStatus expectedStatus = getScheduleStatusForDay(workPattern, date.getDayOfMonth());
            Schedule expectedSchedule = new Schedule(date, expectedStatus);
            expectedSchedule.setUser(user);

            verify(scheduleRepository).save(expectedSchedule);
        }
    }

    @Test
    void generateScheduleForDepartmentActivity_MultipleUsers_ScheduleSavedForEachUser() {
        // Arrange
        BaseUser user1 = new BaseUser();
        BaseUser user2 = new BaseUser();
        YearMonth yearMonth = YearMonth.of(2023, 7);
        String workPattern = "РРРРВОООР";

        // Mock the DepartmentActivityService to return a list of multiple users
        when(departmentActivityService.getUsersByDepartmentActivityId(anyInt()))
                .thenReturn(Arrays.asList(user1, user2));

        // Use anyInt() matcher for departmentActivityId in the method call below
        scheduleService.generateScheduleForDepartmentActivity(yearMonth, workPattern, anyInt());

        // Assert
        verify(scheduleRepository, times(2)).save(any()); // Use any() matcher for Schedule argument
    }

    private ScheduleStatus getScheduleStatusForDay(String workPattern, int dayOfMonth) {
        char c = workPattern.charAt(dayOfMonth - 1);
        if (c == 'Р') {
            return ScheduleStatus.WORKING;
        } else if (c == 'В') {
            return ScheduleStatus.DAY_OFF;
        } else if (c == 'O') {
            return ScheduleStatus.OFF;
        }
        return null;
    }
}

