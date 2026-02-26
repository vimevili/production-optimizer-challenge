package com.projedata.production_optimizer.services;

import com.projedata.production_optimizer.dtos.compositions.CompositionInfoDTO;
import com.projedata.production_optimizer.dtos.products.ProductInfoDTO;
import com.projedata.production_optimizer.exceptions.ResourceNotFoundException;
import com.projedata.production_optimizer.models.Composition;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.models.Product;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MaterialRepository materialRepository;
    private final ProductRepository productRepository;

    public List<ProductInfoDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductInfoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductInfoDTO findById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No product found with id " + productId));
        return ProductInfoDTO.fromEntity(product);
    }

    @Transactional
    public ProductInfoDTO createProduct(ProductInfoDTO productDto) {
        this.validateMaterials(productDto);

        Product product = productRepository.save(Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .build());

        this.saveCompositions(product, productDto.materials());

        return ProductInfoDTO.fromEntity(product);
    }

    @Transactional
    public ProductInfoDTO updateProduct(UUID id, ProductInfoDTO productDto) {
        this.validateMaterials(productDto);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No product found with id " + id));

        product.setName(productDto.name());
        product.setPrice(productDto.price());

        product.getMaterialsNeeded().clear();
        this.saveCompositions(product, productDto.materials());

        return ProductInfoDTO.fromEntity(productRepository.save(product));
    }

    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("No product found with id " + productId));

        productRepository.delete(product);
    }



    // UTILITY METHODS
    private void validateMaterials(ProductInfoDTO dto) {
        if (dto.materials() == null || dto.materials().isEmpty()) {
            throw new IllegalArgumentException("Product must have at least one material");
        }
    }

    private void saveCompositions(Product product, List<CompositionInfoDTO> dtos) {
        if (product.getMaterialsNeeded() == null) {
            product.setMaterialsNeeded(new ArrayList<>());
        }

        for (CompositionInfoDTO compDto : dtos) {
            Material material = materialRepository.findByName(compDto.materialName())
                    .orElseThrow(() -> new ResourceNotFoundException("Material " + compDto.materialName() + " not found"));

            Composition comp = Composition.builder()
                    .product(product)
                    .material(material)
                    .materialQuantity(compDto.quantityNeeded())
                    .build();

            product.getMaterialsNeeded().add(comp);
        }

        productRepository.save(product);
    }
}
