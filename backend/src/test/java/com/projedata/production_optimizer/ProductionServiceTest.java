package com.projedata.production_optimizer;

import com.projedata.production_optimizer.dtos.production.ProductionResponseDTO;
import com.projedata.production_optimizer.models.Composition;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.models.Product;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import com.projedata.production_optimizer.services.ProductionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private ProductionService productionService;

    private Material resin;
    private Product expensiveTank;
    private Product cheapPot;

    @BeforeEach
    void setUp() {
        resin = Material.builder()
                .id(UUID.randomUUID())
                .name("PP Resin")
                .stockQuantity(100.0)
                .build();

        // Product A: R$ 1000 (60g per product) - High priority
        expensiveTank = Product.builder()
                .name("Expensive Tank")
                .price(new BigDecimal("1000.00"))
                .materialsNeeded(new ArrayList<>())
                .build();
        expensiveTank.getMaterialsNeeded().add(
                Composition.builder().material(resin).product(expensiveTank).materialQuantity(60.0).build()
        );

        // Product B: R$ 100 (10g per product) - Low priority
        cheapPot = Product.builder()
                .name("Cheap Pot")
                .price(new BigDecimal("100.00"))
                .materialsNeeded(new ArrayList<>())
                .build();
        cheapPot.getMaterialsNeeded().add(
                Composition.builder().material(resin).product(cheapPot).materialQuantity(10.0).build()
        );
    }

    @Test
    @DisplayName("Scenario 1: Greedy logic - Prioritize high profit and use remaining stock for others")
    void shouldPrioritizeHighProfitAndUseRemainingStock() {
        when(productRepository.findAll(any(Sort.class))).thenReturn(List.of(expensiveTank, cheapPot));
        when(materialRepository.findAll()).thenReturn(List.of(resin));

        ProductionResponseDTO result = productionService.calculateOptimalProduction();


        assertEquals(1, result.suggestedProducts().get(0).quantityToProduce());
        assertEquals(4, result.suggestedProducts().get(1).quantityToProduce());
        assertEquals(new BigDecimal("1400.00"), result.totalPotentialRevenue());
    }

    @Test
    @DisplayName("Scenario 2: Out of stock - Should return empty suggestions when stock is zero")
    void shouldReturnEmptyWhenStockIsZero() {
        resin.setStockQuantity(0.0);
        when(productRepository.findAll(any(Sort.class))).thenReturn(List.of(expensiveTank));
        when(materialRepository.findAll()).thenReturn(List.of(resin));

        ProductionResponseDTO result = productionService.calculateOptimalProduction();

        assertTrue(result.suggestedProducts().isEmpty());
        assertEquals(BigDecimal.ZERO, result.totalPotentialRevenue());
    }

    @Test
    @DisplayName("Scenario 3: Product without composition - Should handle products with no materials")
    void shouldHandleProductWithoutComposition() {
        Product ghostProduct = Product.builder()
                .name("Ghost Product")
                .price(new BigDecimal("500.00"))
                .materialsNeeded(new ArrayList<>())
                .build();

        when(productRepository.findAll(any(Sort.class))).thenReturn(List.of(ghostProduct));
        when(materialRepository.findAll()).thenReturn(List.of(resin));

        ProductionResponseDTO result = productionService.calculateOptimalProduction();

        assertTrue(result.suggestedProducts().isEmpty());
    }
}