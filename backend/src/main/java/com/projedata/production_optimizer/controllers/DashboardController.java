package com.projedata.production_optimizer.controllers;

import com.projedata.production_optimizer.dtos.dashboard.DashboardSummaryDTO;
import com.projedata.production_optimizer.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Aggregated data for the management overview")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @Operation(summary = "Get high-level metrics for dashboard cards")
    public DashboardSummaryDTO getSummary() {
        return dashboardService.getSummary();
    }
}