package com.projedata.production_optimizer.dtos.production;

import java.math.BigDecimal;

public record ProductionSuggestionDTO(
        String productName,
        Integer quantityToProduce,
        BigDecimal unitPrice,
        BigDecimal totalProfit
) {}