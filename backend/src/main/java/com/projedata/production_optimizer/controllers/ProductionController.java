package com.projedata.production_optimizer.controllers;

import com.projedata.production_optimizer.dtos.production.ProductionResponseDTO;
import com.projedata.production_optimizer.services.ProductionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production")
@RequiredArgsConstructor
@Tag(name = "Production", description = "Endpoints for production optimization logic")
public class ProductionController {

    private final ProductionService productionService;

    @GetMapping("/optimize")
    @Operation(summary = "Calculate the best production mix for maximum profit based on current stock")
    public ProductionResponseDTO getOptimization() {
        return productionService.calculateOptimalProduction();
    }
}