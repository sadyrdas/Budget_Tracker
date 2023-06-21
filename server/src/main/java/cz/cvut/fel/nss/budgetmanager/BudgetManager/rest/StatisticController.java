package cz.cvut.fel.nss.budgetmanager.BudgetManager.rest;

import cz.cvut.fel.nss.budgetmanager.BudgetManager.model.TypeInterval;
import cz.cvut.fel.nss.budgetmanager.BudgetManager.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * REST controller for generating statistics.
 */
@RestController
@RequestMapping("rest/statistics")
public class StatisticController {

    private final StatisticsService statisticsService;

    /**
     * Constructs a new StatisticController with the provided StatisticsService.
     *
     * @param statisticsService The StatisticsService to be used.
     */
    public StatisticController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Generates statistics based on the specified interval type.
     *
     * @param intervalType The interval type for the statistics generation.
     * @return A map of statistics, where the keys represent the statistical categories and the values represent the corresponding values.
     */
    @GetMapping(value="/generate")
    public Map<String, BigDecimal> generateStatistics(@RequestParam("intervalType") TypeInterval intervalType) {
        return statisticsService.generateStatistics(intervalType);
    }
}