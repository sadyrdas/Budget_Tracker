package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeInterval;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("rest/statistics")
public class StatisticController {

    private final StatisticsService statisticsService;

    public StatisticController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value="/generate")
    public Map<String, BigDecimal> generateStatistics(@RequestParam("intervalType") TypeInterval intervalType) {
        return statisticsService.generateStatistics(intervalType);
    }
}