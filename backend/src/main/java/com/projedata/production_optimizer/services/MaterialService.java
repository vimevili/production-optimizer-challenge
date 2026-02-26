package com.projedata.production_optimizer.services;

import com.projedata.production_optimizer.dtos.materials.MaterialInfoDTO;
import com.projedata.production_optimizer.exceptions.ResourceNotFoundException;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialService {
    private final MaterialRepository materialRepository;

    public List<MaterialInfoDTO> findAll() {
        return materialRepository.findAll()
                .stream()
                .map(MaterialInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public MaterialInfoDTO findById(UUID materialId) {
        Material material = materialRepository.findById(materialId).orElseThrow(() -> new ResourceNotFoundException("No material found with id " + materialId));
        return MaterialInfoDTO.fromEntity(material);
    }

    @Transactional
    public MaterialInfoDTO create(MaterialInfoDTO dto) {
        validateStock(dto.stockQuantity());

        Material material = Material.builder()
                .name(dto.name())
                .stockQuantity(dto.stockQuantity())
                .unit(dto.unit())
                .build();

        return MaterialInfoDTO.fromEntity(materialRepository.save(material));
    }

    @Transactional
    public MaterialInfoDTO update(UUID id, MaterialInfoDTO dto) {
        validateStock(dto.stockQuantity());

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material not found with id: " + id));

        material.setName(dto.name());
        material.setStockQuantity(dto.stockQuantity());
        material.setUnit(dto.unit());

        return MaterialInfoDTO.fromEntity(materialRepository.save(material));
    }

    @Transactional
    public void delete(UUID id) {
        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Material not found with id: " + id);
        }

        materialRepository.deleteById(id);
    }

    private void validateStock(Double quantity) {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative or null.");
        }
    }
}
