package ee.bcs.colors.infrastructure.rest.controller;

import ee.bcs.colors.domain.dto.MixedColorDto;
import ee.bcs.colors.domain.dto.CreateMixedColorRequest;
import ee.bcs.colors.domain.service.MixedColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mixed-colors")
@RequiredArgsConstructor
@Slf4j
public class MixedColorController {
    
    private final MixedColorService mixedColorService;
    
    @GetMapping
    public ResponseEntity<List<MixedColorDto>> getAllMixedColors() {
        List<MixedColorDto> mixedColors = mixedColorService.getAllMixedColors();
        return ResponseEntity.ok(mixedColors);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MixedColorDto> getMixedColorById(@PathVariable Integer id) {
        MixedColorDto mixedColor = mixedColorService.getMixedColorById(id);
        return ResponseEntity.ok(mixedColor);
    }
    
    @PostMapping
    public ResponseEntity<?> createMixedColor(@Valid @RequestBody CreateMixedColorRequest request) {
        try {
            log.info("Saadud päring segatud värvi loomiseks: color1Id={}, color2Id={}, name={}", 
                    request.getColor1Id(), request.getColor2Id(), request.getName());
            
            MixedColorDto createdMixedColor = mixedColorService.createMixedColor(request);
            log.info("Segatud värv edukalt loodud: id={}", createdMixedColor.getId());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMixedColor);
            
        } catch (RuntimeException e) {
            log.error("Viga segatud värvi loomisel: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Viga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ootamatu viga segatud värvi loomisel", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sisemine serveri viga: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMixedColor(@PathVariable Integer id) {
        try {
            log.info("Kustutan segatud värvi ID-ga: {}", id);
            mixedColorService.deleteMixedColor(id);
            log.info("Segatud värv ID-ga {} edukalt kustutatud", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Viga segatud värvi kustutamisel ID-ga {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("Viga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ootamatu viga segatud värvi kustutamisel ID-ga {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sisemine serveri viga: " + e.getMessage());
        }
    }
    
    @GetMapping("/by-color/{colorId}")
    public ResponseEntity<List<MixedColorDto>> getMixedColorsByColorId(@PathVariable Integer colorId) {
        List<MixedColorDto> mixedColors = mixedColorService.getMixedColorsByColorId(colorId);
        return ResponseEntity.ok(mixedColors);
    }
}
