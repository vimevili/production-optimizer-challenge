package com.projedata.production_optimizer;

import com.projedata.production_optimizer.dtos.materials.MaterialInfoDTO;
import com.projedata.production_optimizer.exceptions.ResourceNotFoundException;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.services.MaterialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @InjectMocks
    private MaterialService materialService;

    private Material material;
    private UUID materialId;

    @BeforeEach
    void setUp() {
        materialId = UUID.randomUUID();
        material = Material.builder().id(materialId).name("PP Resin").build();
    }

    @Test
    void findById_Success() {
        when(materialRepository.findById(materialId)).thenReturn(Optional.of(material));

        MaterialInfoDTO result = materialService.findById(materialId);

        assertNotNull(result);
        assertEquals("PP Resin", result.name());
    }

    @Test
    void findById_NotFound_ShouldThrowException() {
        UUID wrongId = UUID.randomUUID();
        when(materialRepository.findById(wrongId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> materialService.findById(wrongId));
    }
}