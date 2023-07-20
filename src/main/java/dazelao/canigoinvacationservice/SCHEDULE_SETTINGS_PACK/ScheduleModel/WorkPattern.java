package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel;

import java.util.List;

public class WorkPattern {

    private final List<ScheduleStatus> workDays;

    public WorkPattern(List<ScheduleStatus> workDays) {
        this.workDays = workDays;
    }

    public List<ScheduleStatus> getWorkDays() {
        return workDays;
    }

    public ScheduleStatus getStatusForDay(int dayOfMonth) {
        int index = (dayOfMonth - 1) % workDays.size();
        return workDays.get(index);
    }
}
