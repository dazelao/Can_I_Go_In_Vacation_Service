package dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationController;


import dazelao.canigoinvacationservice.VACATION_SETTING_PACK.VacationService.CanIHaveVacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/canihavevacation")
public class CanVacationController {
    private final CanIHaveVacationService canIHaveVacationService;

    @Autowired
    public CanVacationController(CanIHaveVacationService canIHaveVacationService) {
        this.canIHaveVacationService = canIHaveVacationService;
    }

    @GetMapping("/checkVacationEligibility/{userId}")
    public Map<String, Object> checkVacationEligibility(@PathVariable Long userId) {
        boolean canUserHaveVacation = canIHaveVacationService.canUserHaveVacation(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("canUserHaveVacation", canUserHaveVacation);
        return response;
    }

    @PostMapping("/checkVacationEligibility/{userId}")
    public Map<String, Object> checkVacationEligibilityForDates(
            @PathVariable Long userId,
            @RequestBody List<LocalDate> vacationDates
    ) {
        return canIHaveVacationService.canUserHaveVacation(userId, vacationDates);
    }
}


