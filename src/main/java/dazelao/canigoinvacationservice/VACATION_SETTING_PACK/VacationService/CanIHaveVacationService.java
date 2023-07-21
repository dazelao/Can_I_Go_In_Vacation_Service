package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationService;


import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Activity;
import dazelao.canigoinvacationservice.DEPARTMENTS_PACK.Departments.Department;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleModel.ScheduleStatus;
import dazelao.canigoinvacationservice.SCHEDULE_SETTINGS_PACK.ScheduleService.ScheduleService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Service.UserService;
import dazelao.canigoinvacationservice.USER_SETTINGS_PACK.Users.BaseUser;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationParam.VacationParam;
import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationRepository.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CanIHaveVacationService {

    private final VacationService vacationService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final VacationRepository vacationRepository;

    @Autowired
    public CanIHaveVacationService(VacationService vacationService, UserService userService, ScheduleService scheduleService, VacationRepository vacationRepository) {
        this.vacationService = vacationService;
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.vacationRepository = vacationRepository;
    }

    public Optional<VacationParam> getLastActivityVacationCountByActivityId(Integer activityId) {
        Activity activity = new Activity();
        activity.setId(activityId);
        return vacationRepository.findFirstByActivityOrderByUpdatedAtDesc(activity);
    }

    public Optional<VacationParam> getLastDepartmentVacationCountByDepartmentId(Integer departmentId) {
        Department department = new Department();
        department.setId(departmentId);
        return vacationRepository.findFirstByDepartmentOrderByUpdatedAtDesc(department);
    }


    public boolean canUserHaveVacation(Long userId) {
        Optional<BaseUser> optionalUser = userService.getUserById(userId);

        if (optionalUser.isPresent()) {
            BaseUser user = optionalUser.get();
            Integer activityId = user.getDepartmentActivity().getActivity().getId();
            Integer departmentId = user.getDepartmentActivity().getDepartment().getId();

            Optional<VacationParam> lastActivityVacationCount = getLastActivityVacationCountByActivityId(activityId);
            Optional<VacationParam> lastDepartmentVacationCount = getLastDepartmentVacationCountByDepartmentId(departmentId);

            System.out.println("Активность " + activityId);
            System.out.println("Департамент " + departmentId);


            int activityVacationCount = lastActivityVacationCount.map(VacationParam::getActivityVacationCount).orElse(0);
            int departmentVacationCount = lastDepartmentVacationCount.map(VacationParam::getDepartmentVacationCount).orElse(0);

            System.out.println("Квота на Активность " + activityVacationCount);
            System.out.println("Квота на Департамент " + departmentVacationCount);

            return true;
        }

        return false;
    }

//    public boolean canUserHaveVacation(Long userId, List<LocalDate> vacationDates) {
//        Optional<BaseUser> optionalUser = userService.getUserById(userId);
//
//        if (optionalUser.isPresent()) {
//            BaseUser user = optionalUser.get();
//            Integer activityId = user.getDepartmentActivity().getActivity().getId();
//            Integer departmentId = user.getDepartmentActivity().getDepartment().getId();
//
//            Optional<VacationParam> lastActivityVacationCount = getLastActivityVacationCountByActivityId(activityId);
//            Optional<VacationParam> lastDepartmentVacationCount = getLastDepartmentVacationCountByDepartmentId(departmentId);
//
//            int activityVacationCount = lastActivityVacationCount.map(VacationParam::getActivityVacationCount).orElse(0);
//            int departmentVacationCount = lastDepartmentVacationCount.map(VacationParam::getDepartmentVacationCount).orElse(0);
//
//            System.out.println("Activity ID: " + activityId);
//            System.out.println("Department ID: " + departmentId);
//            System.out.println("Last Activity Vacation Count: " + activityVacationCount);
//            System.out.println("Last Department Vacation Count: " + departmentVacationCount);
//
//            List<LocalDate> exceededDates = new ArrayList<>();
//
//            for (LocalDate date : vacationDates) {
//                int usersOnVacation = scheduleService.getUsersCountByStatusAndDate(ScheduleStatus.VACATION, date);
//                System.out.println("Users on vacation on " + date + ": " + usersOnVacation);
//
//                if (usersOnVacation >= activityVacationCount || usersOnVacation >= departmentVacationCount) {
//                    exceededDates.add(date);
//                }
//            }
//
//            if (!exceededDates.isEmpty()) {
//                System.out.println("Отпуск не доступен, лимит activityId или departmentId превышен для следующих дат:");
//                for (LocalDate date : exceededDates) {
//                    System.out.println(date);
//                }
//                return false;
//            }
//
//            System.out.println("Отпуск доступен на все указанные даты!");
//            return true;
//        }
//
//        return false;
//    }

    public Map<String, Object> canUserHaveVacation(Long userId, List<LocalDate> vacationDates) {
        Map<String, Object> response = new HashMap<>();

        Optional<BaseUser> optionalUser = userService.getUserById(userId);

        if (optionalUser.isPresent()) {
            BaseUser user = optionalUser.get();
            Integer activityId = user.getDepartmentActivity().getActivity().getId();
            Integer departmentId = user.getDepartmentActivity().getDepartment().getId();

            Optional<VacationParam> lastActivityVacationCount = getLastActivityVacationCountByActivityId(activityId);
            Optional<VacationParam> lastDepartmentVacationCount = getLastDepartmentVacationCountByDepartmentId(departmentId);

            int activityVacationCount = lastActivityVacationCount.map(VacationParam::getActivityVacationCount).orElse(0);
            int departmentVacationCount = lastDepartmentVacationCount.map(VacationParam::getDepartmentVacationCount).orElse(0);

            List<LocalDate> exceededDates = new ArrayList<>();

            for (LocalDate date : vacationDates) {
                int usersOnVacation = scheduleService.getUsersCountByStatusAndDate(ScheduleStatus.VACATION, date);

                if (usersOnVacation >= activityVacationCount || usersOnVacation >= departmentVacationCount) {
                    exceededDates.add(date);
                }
            }

            if (!exceededDates.isEmpty()) {
                response.put("canUserHaveVacation", false);
                response.put("exceededDates", exceededDates);
            } else {
                response.put("canUserHaveVacation", true);
            }
        } else {
            response.put("canUserHaveVacation", false);
        }

        return response;
    }


}
