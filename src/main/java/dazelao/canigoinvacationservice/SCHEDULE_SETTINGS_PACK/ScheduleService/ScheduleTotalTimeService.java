package dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService;


import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.Schedule;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleRequest;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleRepo.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class ScheduleTotalTimeService {

    @Autowired
    private ScheduleRepository scheduleRepository;

//    public Map<Long, String> getTotalTimeByUserIdAndMonth(ScheduleRequest scheduleRequest) {
//        int year = scheduleRequest.getYear();
//        Month month = scheduleRequest.getMonthEnum();
//
//        long startTime = System.currentTimeMillis();
//
//        List<Schedule> schedules = scheduleRepository.findByDateBetween(
//                LocalDate.of(year, month, 1),
//                LocalDate.of(year, month, YearMonth.of(year, month.getValue()).lengthOfMonth())
//        );
//
//        Map<Long, Duration> totalTimeMap = new HashMap<>();
//
//        for (Schedule schedule : schedules) {
//            if (schedule.getStatus() == ScheduleStatus.WORKING) {
//                Long userId = schedule.getUser().getId();
//                Duration duration = Duration.between(schedule.getStartTime(), schedule.getEndTime());
//                totalTimeMap.put(userId, totalTimeMap.getOrDefault(userId, Duration.ZERO).plus(duration));
//            }
//        }
//
//        long endTime = System.currentTimeMillis();
//        long executionTime = endTime - startTime;
//        System.out.println("Execution time: " + executionTime + " milliseconds");
//
//        return totalTimeMap.entrySet().stream()
//                .collect(Collectors.toMap(Map.Entry::getKey, entry -> formatDuration(entry.getValue())));
//    }
//
//    private String formatDuration(Duration duration) {
//        long hours = duration.toHours();
//        long minutes = duration.minusHours(hours).toMinutes();
//        return hours + " часов " + minutes + " минут";
//    }


public Map<Long, String> getTotalTimeByUserIdAndMonth(ScheduleRequest scheduleRequest) throws InterruptedException, ExecutionException {
    long startTime = System.currentTimeMillis();
    int year = scheduleRequest.getYear();
    Month month = scheduleRequest.getMonthEnum();


    List<Schedule> schedules = scheduleRepository.findByDateBetween(
            LocalDate.of(year, month, 1),
            LocalDate.of(year, month, YearMonth.of(year, month.getValue()).lengthOfMonth())
    );


    int mid = schedules.size() / 2;
    List<Schedule> part1 = schedules.subList(0, mid);
    List<Schedule> part2 = schedules.subList(mid, schedules.size());

    ExecutorService executorService = Executors.newFixedThreadPool(2);


    Future<Map<Long, Duration>> futureResult1 = executorService.submit(() -> processList(part1));
    Future<Map<Long, Duration>> futureResult2 = executorService.submit(() -> processList(part2));


    Map<Long, Duration> totalTimeMap1 = futureResult1.get();
    Map<Long, Duration> totalTimeMap2 = futureResult2.get();

    // Схлопываем результаты из каждой части
    Map<Long, Duration> totalTimeMap = mergeResults(totalTimeMap1, totalTimeMap2);

    // Останавливаем ExecutorService
    executorService.shutdown();

    long endTime = System.currentTimeMillis();
    System.out.println("Время выполнения метода getTotalTimeByUserIdAndMonth: " + (endTime - startTime) + " мс");
    return totalTimeMap.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> formatDuration(entry.getValue())));
}

    // Обрабатываем расписание и считаем общее время
    private Map<Long, Duration> processList(List<Schedule> schedules) {
        Map<Long, Duration> totalTimeMap = new HashMap<>();

        for (Schedule schedule : schedules) {
            if (schedule.getStatus() == ScheduleStatus.WORKING) {
                Long userId = schedule.getUser().getId();
                Duration duration = Duration.between(schedule.getStartTime(), schedule.getEndTime());
                totalTimeMap.put(userId, totalTimeMap.getOrDefault(userId, Duration.ZERO).plus(duration));
            }
        }

        return totalTimeMap;
    }

    // Схлопываем списки
    private Map<Long, Duration> mergeResults(Map<Long, Duration> map1, Map<Long, Duration> map2) {
        Map<Long, Duration> mergedMap = new HashMap<>();
        mergedMap.putAll(map1);
        map2.forEach((key, value) -> mergedMap.merge(key, value, Duration::plus));
        return mergedMap;
    }

    // Обьеденяем и возвращаем строку
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        return hours + " часов " + minutes + " минут";
    }
}



