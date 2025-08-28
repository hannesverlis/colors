package ee.bcs.colors.infrastructure.repository;

import ee.bcs.colors.domain.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    
    Optional<Color> findByNameIgnoreCase(String name);
    
    boolean existsByNameIgnoreCase(String name);
}
