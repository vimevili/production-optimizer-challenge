package com.projedata.production_optimizer.dtos.production;

import java.math.BigDecimal;
import java.util.List;

public record ProductionResponseDTO(
        BigDecimal totalPotentialRevenue,
        List<ProductionSuggestionDTO> suggestedProducts
) {}