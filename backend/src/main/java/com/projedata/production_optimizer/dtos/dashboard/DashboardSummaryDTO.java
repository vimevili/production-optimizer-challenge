package com.projedata.production_optimizer.dtos.dashboard;

import java.math.BigDecimal;

public record DashboardSummaryDTO(
        long totalProducts,
        long totalMaterials,
        long lowStockCount,
        BigDecimal potentialRevenue
) {}