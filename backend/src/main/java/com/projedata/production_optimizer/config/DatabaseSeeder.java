package com.projedata.production_optimizer.config;

import com.projedata.production_optimizer.models.Composition;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.models.Product;
import com.projedata.production_optimizer.repositories.CompositionRepository;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("dev")
public class DatabaseSeeder {
    @Bean
    CommandLineRunner initDatabase(
            MaterialRepository materialRepo,
            ProductRepository productRepo,
            CompositionRepository compRepo
    ) {
        return args -> {
            //Materials
            Material ppResin = Material.builder().name("PP Resin").stockQuantity(500000.00).build();
            Material blackMasterbatch = Material.builder().name("Black Masterbatch").stockQuantity(5000.00).build();
            Material goldenMasterbatch = Material.builder().name("Golden Masterbatch").stockQuantity(500.00).build();
            Material uvAdditive = Material.builder().name("UV Additive").stockQuantity(2000.00).build();
            Material reinforcingFiber = Material.builder().name("Reinforcing Fiber").stockQuantity(10000.00).build();
            materialRepo.saveAll(List.of(ppResin, blackMasterbatch, goldenMasterbatch, uvAdditive, reinforcingFiber));

            //Products
            Product tank = Product.builder().name("Premium Industrial Tank").price(new BigDecimal("2450.00")).build();
            Product crate = Product.builder().name("Standard Logistics Crate").price(new BigDecimal("180.00")).build();
            Product pot = Product.builder().name("Basic Garden Pot ").price(new BigDecimal("85.00")).build();
            productRepo.saveAll(List.of(tank, crate, pot));

            // Tank Compositions
            Composition tankComposition1 = Composition.builder().product(tank).material(ppResin).materialQuantity(20000.00).build();
            Composition tankComposition2 = Composition.builder().product(tank).material(goldenMasterbatch).materialQuantity(100.00).build();
            Composition tankComposition3 = Composition.builder().product(tank).material(uvAdditive).materialQuantity(500.00).build();
            Composition tankComposition4 = Composition.builder().product(tank).material(reinforcingFiber).materialQuantity(2000.00).build();
            compRepo.saveAll(List.of(tankComposition1, tankComposition2, tankComposition3, tankComposition4));

            // Crate Compositions
            Composition crateComposition1 = Composition.builder().product(crate).material(ppResin).materialQuantity(5000.00).build();
            Composition crateComposition2 = Composition.builder().product(crate).material(blackMasterbatch).materialQuantity(150.00).build();
            Composition crateComposition3 = Composition.builder().product(crate).material(reinforcingFiber).materialQuantity(500.00).build();
            compRepo.saveAll(List.of(crateComposition1, crateComposition2, crateComposition3));

            // Pot Compositions
            Composition potComposition1 = Composition.builder().product(pot).material(ppResin).materialQuantity(1000.00).build();
            Composition potComposition2 = Composition.builder().product(pot).material(blackMasterbatch).materialQuantity(20.00).build();
            compRepo.saveAll(List.of(potComposition1, potComposition2));

            System.out.println("Database seeded successfully!");
        };
    }
}

