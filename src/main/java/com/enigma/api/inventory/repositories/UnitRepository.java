package com.enigma.api.inventory.repositories;

import com.enigma.api.inventory.entities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
}
