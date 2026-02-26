package com.projedata.production_optimizer.dtos.compositions;

import com.projedata.production_optimizer.enums.UnitOfMeasure;
import com.projedata.production_optimizer.models.Composition;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CompositionInfoDTO(
        @Schema(description = "Unique composition identifier")
        UUID id,

        @Schema(description = "Material name")
        String materialName,

        @Schema(description = "Material quantity needed")
        Double quantityNeeded,

        @Schema(description = "Material unit of measure")
        UnitOfMeasure unit
) {
        public static CompositionInfoDTO fromEntity(Composition composition) {
                return new CompositionInfoDTO(
                        composition.getMaterial().getId(),
                        composition.getMaterial().getName(),
                        composition.getMaterialQuantity(),
                        composition.getMaterial().getUnit()
                );
        }
}

