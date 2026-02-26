package com.projedata.production_optimizer.dtos.materials;

import com.projedata.production_optimizer.enums.UnitOfMeasure;
import com.projedata.production_optimizer.models.Material;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record MaterialInfoDTO(
        @Schema(description = "Unique identifier of the material")
        UUID id,

        @NotBlank(message = "Name is required")
        @Schema(example = "PP Resin")
        String name,

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        @Schema(example = "500000.0")
        Double stockQuantity,

        @NotNull(message = "Unit of measure is required")
        @Schema(description = "Unit of measure", example = "GRAMS")
        UnitOfMeasure unit
) {

        public static MaterialInfoDTO fromEntity(Material material) {
                return new MaterialInfoDTO(
                        material.getId(),
                        material.getName(),
                        material.getStockQuantity(),
                        material.getUnit()
                );
        }
}