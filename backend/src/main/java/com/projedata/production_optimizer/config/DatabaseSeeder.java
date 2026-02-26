package com.projedata.production_optimizer.config;

import com.projedata.production_optimizer.enums.UnitOfMeasure;
import com.projedata.production_optimizer.models.Composition;
import com.projedata.production_optimizer.models.Material;
import com.projedata.production_optimizer.models.Product;
import com.projedata.production_optimizer.repositories.CompositionRepository;
import com.projedata.production_optimizer.repositories.MaterialRepository;
import com.projedata.production_optimizer.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DatabaseSeeder {

    @Bean
    CommandLineRunner initDatabase(
            MaterialRepository materialRepo,
            ProductRepository productRepo,
            CompositionRepository compRepo
    ) {
        return args -> {
            // --- MATERIALS ---
            Material ppResin = Material.builder().name("PP Resin").stockQuantity(800000.0).unit(UnitOfMeasure.GRAMS).build();
            Material recycledPlastic = Material.builder().name("Recycled Plastic").stockQuantity(1500000.0).unit(UnitOfMeasure.GRAMS).build();
            Material reinforcingFiber = Material.builder().name("Reinforcing Fiber").stockQuantity(25000.0).unit(UnitOfMeasure.GRAMS).build();

            Material blackMaster = Material.builder().name("Black Masterbatch").stockQuantity(15000.0).unit(UnitOfMeasure.GRAMS).build();
            Material goldenMaster = Material.builder().name("Golden Masterbatch").stockQuantity(1200.0).unit(UnitOfMeasure.GRAMS).build();
            Material whiteMaster = Material.builder().name("White Masterbatch").stockQuantity(10000.0).unit(UnitOfMeasure.GRAMS).build();
            Material blueMaster = Material.builder().name("Blue Masterbatch").stockQuantity(8000.0).unit(UnitOfMeasure.GRAMS).build();

            Material uvAdditive = Material.builder().name("UV Additive").stockQuantity(5000.0).unit(UnitOfMeasure.GRAMS).build();
            Material impactModifier = Material.builder().name("Impact Modifier").stockQuantity(3000.0).unit(UnitOfMeasure.GRAMS).build();

            materialRepo.saveAll(List.of(ppResin, recycledPlastic, reinforcingFiber, blackMaster, goldenMaster, whiteMaster, blueMaster, uvAdditive, impactModifier));

            // --- PRODUCTS ---
            Product tank = Product.builder().name("Premium Industrial Tank").price(new BigDecimal("3200.00")).build();
            Product crate = Product.builder().name("Standard Logistics Crate").price(new BigDecimal("210.00")).build();
            Product pot = Product.builder().name("Basic Garden Pot").price(new BigDecimal("45.00")).build();
            Product ecoBench = Product.builder().name("Eco-Friendly Park Bench").price(new BigDecimal("850.00")).build();
            Product blueBarrel = Product.builder().name("Industrial Blue Barrel").price(new BigDecimal("450.00")).build();
            Product whitePallet = Product.builder().name("Heavy Duty White Pallet").price(new BigDecimal("1200.00")).build();

            productRepo.saveAll(List.of(tank, crate, pot, ecoBench, blueBarrel, whitePallet));

            // --- COMPOSITIONS ---

            compRepo.saveAll(List.of(
                    Composition.builder().product(tank).material(ppResin).materialQuantity(25000.0).build(),
                    Composition.builder().product(tank).material(goldenMaster).materialQuantity(150.0).build(),
                    Composition.builder().product(tank).material(uvAdditive).materialQuantity(800.0).build(),
                    Composition.builder().product(tank).material(reinforcingFiber).materialQuantity(3000.0).build()
            ));

            compRepo.saveAll(List.of(
                    Composition.builder().product(ecoBench).material(recycledPlastic).materialQuantity(40000.0).build(),
                    Composition.builder().product(ecoBench).material(blackMaster).materialQuantity(200.0).build(),
                    Composition.builder().product(ecoBench).material(reinforcingFiber).materialQuantity(5000.0).build()
            ));

            compRepo.saveAll(List.of(
                    Composition.builder().product(whitePallet).material(ppResin).materialQuantity(15000.0).build(),
                    Composition.builder().product(whitePallet).material(whiteMaster).materialQuantity(300.0).build(),
                    Composition.builder().product(whitePallet).material(reinforcingFiber).materialQuantity(4000.0).build(),
                    Composition.builder().product(whitePallet).material(impactModifier).materialQuantity(500.0).build()
            ));

            compRepo.saveAll(List.of(
                    Composition.builder().product(blueBarrel).material(ppResin).materialQuantity(8000.0).build(),
                    Composition.builder().product(blueBarrel).material(blueMaster).materialQuantity(100.0).build(),
                    Composition.builder().product(blueBarrel).material(uvAdditive).materialQuantity(200.0).build()
            ));

            compRepo.saveAll(List.of(
                    Composition.builder().product(crate).material(recycledPlastic).materialQuantity(5000.0).build(),
                    Composition.builder().product(crate).material(blackMaster).materialQuantity(100.0).build()
            ));

            compRepo.saveAll(List.of(
                    Composition.builder().product(pot).material(recycledPlastic).materialQuantity(1200.0).build(),
                    Composition.builder().product(pot).material(blackMaster).materialQuantity(30.0).build()
            ));

            System.out.println(">>> Database seeded successfully!");
        };
    }
}