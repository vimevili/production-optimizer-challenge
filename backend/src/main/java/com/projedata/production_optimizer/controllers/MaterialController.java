package com.projedata.production_optimizer.controllers;

import com.projedata.production_optimizer.dtos.materials.MaterialInfoDTO;
import com.projedata.production_optimizer.enums.UnitOfMeasure;
import com.projedata.production_optimizer.services.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@Tag(name = "Materials", description = "Operations for raw material inventory management")
public class MaterialController {

    private final MaterialService materialService;

    @GetMapping
    @Operation(summary = "List all materials in stock")
    public List<MaterialInfoDTO> getAll() {
        return materialService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific material by ID")
    public MaterialInfoDTO getById(@PathVariable UUID id) {
        return materialService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new raw material")
    public MaterialInfoDTO create(@RequestBody MaterialInfoDTO materialDto) {
        return materialService.create(materialDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing material's name, stock or unit")
    public MaterialInfoDTO update(@PathVariable UUID id, @RequestBody MaterialInfoDTO materialDto) {
        return materialService.update(id, materialDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a material from inventory")
    public void delete(@PathVariable UUID id) {
        materialService.delete(id);
    }

    @GetMapping("/units")
    @Operation(summary = "Get all valid units of measure for the frontend dropdown")
    public List<UnitOfMeasure> getUnits() {
        // Retorna todos os valores do Enum (GRAMS, UNIT, etc) como uma lista
        return Arrays.asList(UnitOfMeasure.values());
    }
}

