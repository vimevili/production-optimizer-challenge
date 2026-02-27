package com.projedata.production_optimizer;
import com.projedata.production_optimizer.dtos.dashboard.DashboardSummaryDTO;
import com.projedata.production_optimizer.dtos.production.ProductionResponseDTO;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import com.projedata.production_optimizer.services.DashboardService;
import com.projedata.production_optimizer.services.ProductionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private MaterialRepository materialRepository;
    @Mock
    private ProductionService productionService;

    @InjectMocks
    private DashboardService dashboardService;

    private ProductionResponseDTO mockProduction;

    @BeforeEach
    void setUp() {
        mockProduction = new ProductionResponseDTO(new BigDecimal("5000.00"), new ArrayList<>());
    }

    @Test
    void getSummary_Success() {
        when(productRepository.count()).thenReturn(10L);
        when(materialRepository.count()).thenReturn(5L);
        when(productionService.calculateOptimalProduction()).thenReturn(mockProduction);

        DashboardSummaryDTO summary = dashboardService.getSummary();

        assertEquals(10L, summary.totalProducts());
        assertEquals(5L, summary.totalMaterials());
        assertEquals(new BigDecimal("5000.00"), summary.potentialRevenue());
    }
}