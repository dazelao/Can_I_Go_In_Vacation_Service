package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationController;


import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationService.CanIHaveVacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/canihavevacation")
@Tag(name = "Отпуск", description ="Работа с проверками доступности отпуска")
public class CanVacationController {
    private final CanIHaveVacationService canIHaveVacationService;

    @Autowired
    public CanVacationController(CanIHaveVacationService canIHaveVacationService) {
        this.canIHaveVacationService = canIHaveVacationService;
    }

    @GetMapping("/checkVacationEligibility/{userId}")
    @Operation(summary = "Проверка возможности отгула")
    public Map<String, Object> checkVacationEligibility(@PathVariable Long userId) {
        boolean canUserHaveVacation = canIHaveVacationService.canUserHaveVacation(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("canUserHaveVacation", canUserHaveVacation);
        return response;
    }

    @PostMapping("/checkVacationEligibility/{userId}")
    @Operation(summary = "Проверка возможности отпуска на промежуток")
    public Map<String, Object> checkVacationEligibilityForDates(
            @PathVariable Long userId,
            @RequestBody List<LocalDate> vacationDates
    ) {
        return canIHaveVacationService.canUserHaveVacation(userId, vacationDates);
    }
}


