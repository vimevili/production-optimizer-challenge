package com.projedata.production_optimizer.repositories;

import com.projedata.production_optimizer.models.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {
}
