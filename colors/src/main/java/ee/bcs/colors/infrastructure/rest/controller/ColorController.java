package ee.bcs.colors.infrastructure.rest.controller;

import ee.bcs.colors.domain.dto.ColorDto;
import ee.bcs.colors.domain.dto.CreateColorRequest;
import ee.bcs.colors.domain.service.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
@Slf4j
public class ColorController {
    
    private final ColorService colorService;
    
    @GetMapping
    public ResponseEntity<List<ColorDto>> getAllColors() {
        List<ColorDto> colors = colorService.getAllColors();
        log.info("Tagastatakse {} värvi", colors.size());
        return ResponseEntity.ok(colors);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getColorById(@PathVariable Integer id) {
        try {
            log.info("Otsin värvi ID-ga: {}", id);
            ColorDto color = colorService.getColorById(id);
            return ResponseEntity.ok(color);
        } catch (RuntimeException e) {
            log.error("Viga värvi otsimisel ID-ga {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Viga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ootamatu viga värvi otsimisel ID-ga {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sisemine serveri viga: " + e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<?> createColor(@Valid @RequestBody CreateColorRequest request) {
        try {
            log.info("Loon uue värvi: name={}, r={}, g={}, b={}", 
                    request.getName(), request.getR(), request.getG(), request.getB());
            ColorDto createdColor = colorService.createColor(request);
            log.info("Värv edukalt loodud: id={}, name={}", createdColor.getId(), createdColor.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdColor);
        } catch (RuntimeException e) {
            log.error("Viga värvi loomisel: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Viga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ootamatu viga värvi loomisel", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sisemine serveri viga: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ColorDto> updateColor(@PathVariable Integer id, 
                                               @Valid @RequestBody CreateColorRequest request) {
        ColorDto updatedColor = colorService.updateColor(id, request);
        return ResponseEntity.ok(updatedColor);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteColor(@PathVariable Integer id) {
        try {
            log.info("Kustutan värvi ID-ga: {}", id);
            colorService.deleteColor(id);
            log.info("Värv ID-ga {} edukalt kustutatud", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Viga värvi kustutamisel ID-ga {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body("Viga: " + e.getMessage());
        } catch (Exception e) {
            log.error("Ootamatu viga värvi kustutamisel ID-ga {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Sisemine serveri viga: " + e.getMessage());
        }
    }
    
    // Debug endpoint värvide kontrollimiseks
    @GetMapping("/debug/all")
    public ResponseEntity<String> debugAllColors() {
        List<ColorDto> colors = colorService.getAllColors();
        StringBuilder sb = new StringBuilder();
        sb.append("Kõik värvid andmebaasis:\n");
        for (ColorDto color : colors) {
            sb.append(String.format("ID: %d, Nimi: %s, RGB: (%d,%d,%d), HEX: %s\n", 
                    color.getId(), color.getName(), color.getR(), color.getG(), color.getB(), color.getHex()));
        }
        return ResponseEntity.ok(sb.toString());
    }
}
