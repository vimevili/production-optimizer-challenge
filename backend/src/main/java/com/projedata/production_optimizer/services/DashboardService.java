package com.projedata.production_optimizer.services;

import com.projedata.production_optimizer.dtos.dashboard.DashboardSummaryDTO;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final MaterialRepository materialRepository;
    private final ProductionService productionService;

    public DashboardSummaryDTO getSummary() {
        long products = productRepository.count();
        long materials = materialRepository.count();

        long lowStock = materialRepository.findAll().stream()
                .filter(m -> m.getStockQuantity() < 5000.0)
                .count();

        BigDecimal revenue = productionService.calculateOptimalProduction().totalPotentialRevenue();

        return new DashboardSummaryDTO(products, materials, lowStock, revenue);
    }
}