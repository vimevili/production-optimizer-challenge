package com.projedata.production_optimizer.services;

import com.projedata.production_optimizer.dtos.production.ProductionResponseDTO;
import com.projedata.production_optimizer.dtos.production.ProductionSuggestionDTO;
import com.projedata.production_optimizer.models.Composition;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.models.Product;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionService {

    private final ProductRepository productRepository;
    private final MaterialRepository materialRepository;

    public ProductionResponseDTO calculateOptimalProduction() {
        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));

        Map<UUID, Double> virtualStock = materialRepository.findAll().stream()
                .collect(Collectors.toMap(Material::getId, Material::getStockQuantity));

        List<ProductionSuggestionDTO> suggestions = new ArrayList<>();
        BigDecimal totalPotentialRevenue = BigDecimal.ZERO;

        for (Product product : products) {
            if (product.getMaterialsNeeded() == null || product.getMaterialsNeeded().isEmpty()) {
                continue;
            }
            int quantityProduced = 0;
            boolean canProduceMore = true;

            while (canProduceMore) {
                for (Composition comp : product.getMaterialsNeeded()) {
                    Double available = virtualStock.get(comp.getMaterial().getId());
                    if (available == null || available < comp.getMaterialQuantity()) {
                        canProduceMore = false;
                        break;
                    }
                }

                if (canProduceMore) {
                    for (Composition comp : product.getMaterialsNeeded()) {
                        UUID materialId = comp.getMaterial().getId();
                        virtualStock.put(materialId, virtualStock.get(materialId) - comp.getMaterialQuantity());
                    }
                    quantityProduced++;
                }
            }

            if (quantityProduced > 0) {
                BigDecimal unitPrice = product.getPrice();
                BigDecimal totalProfitFromProduct = unitPrice.multiply(BigDecimal.valueOf(quantityProduced));

                suggestions.add(new ProductionSuggestionDTO(
                        product.getName(),
                        quantityProduced,
                        unitPrice,
                        totalProfitFromProduct
                ));

                totalPotentialRevenue = totalPotentialRevenue.add(totalProfitFromProduct);
            }
        }

        return new ProductionResponseDTO(totalPotentialRevenue, suggestions);
    }
}