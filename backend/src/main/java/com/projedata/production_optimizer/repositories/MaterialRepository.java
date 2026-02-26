package com.projedata.production_optimizer.repositories;

import com.projedata.production_optimizer.models.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MaterialRepository extends JpaRepository<Material, UUID> {
    Optional<Material> findByName(String name);
}
