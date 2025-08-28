package ee.bcs.colors.infrastructure.repository;

import ee.bcs.colors.domain.entity.MixedColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MixedColorRepository extends JpaRepository<MixedColor, Integer> {
    
    @Query("SELECT mc FROM MixedColor mc WHERE (mc.color1.id = :color1Id AND mc.color2.id = :color2Id) OR (mc.color1.id = :color2Id AND mc.color2.id = :color1Id)")
    Optional<MixedColor> findByColorPair(@Param("color1Id") Integer color1Id, @Param("color2Id") Integer color2Id);
    
    @Query("SELECT mc FROM MixedColor mc WHERE mc.color1.id = :colorId OR mc.color2.id = :colorId")
    List<MixedColor> findByColorId(@Param("colorId") Integer colorId);
}
