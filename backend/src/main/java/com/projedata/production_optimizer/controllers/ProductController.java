package com.projedata.production_optimizer.controllers;

import com.projedata.production_optimizer.dtos.products.ProductInfoDTO;
import com.projedata.production_optimizer.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Endpoints for basic Product operations")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "List all products")
    public List<ProductInfoDTO> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specific product by UUID")
    public ProductInfoDTO getById(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product with its composition")
    public ProductInfoDTO create(@RequestBody ProductInfoDTO productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product and its composition")
    public ProductInfoDTO update(@PathVariable UUID id, @RequestBody ProductInfoDTO productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product")
    public void delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
