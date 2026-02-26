package com.projedata.production_optimizer.dtos.products;

import com.projedata.production_optimizer.dtos.compositions.CompositionInfoDTO;
import com.projedata.production_optimizer.models.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductInfoDTO(

        @Schema(description = "Unique product identifier")
        UUID id,

        @Schema(description = "Product name")
        String name,

        @Schema(description = "Product price")
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @Schema(implementation = CompositionInfoDTO.class, description = "List of materials and their quantities needed for crafting the product")
        @NotEmpty(message = "A product must have at least one material")
        List<CompositionInfoDTO> materials
) {
        public static ProductInfoDTO fromEntity(Product product) {
                return new ProductInfoDTO(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getMaterialsNeeded() == null ? List.of() :
                                product.getMaterialsNeeded().stream()
                                        .map(CompositionInfoDTO::fromEntity)
                                        .toList()
                );
        }
}
