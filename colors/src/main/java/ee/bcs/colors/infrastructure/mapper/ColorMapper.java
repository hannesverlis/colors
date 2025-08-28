package ee.bcs.colors.infrastructure.mapper;

import ee.bcs.colors.domain.dto.ColorDto;
import ee.bcs.colors.domain.dto.CreateColorRequest;
import ee.bcs.colors.domain.entity.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper {
    
    public ColorDto toDto(Color color) {
        if (color == null) return null;
        
        return new ColorDto(
            color.getId(),
            color.getName(),
            color.getR(),
            color.getG(),
            color.getB(),
            color.getHex()
        );
    }
    
    public Color toEntity(CreateColorRequest request) {
        if (request == null) return null;
        
        Color color = new Color();
        // ID määratakse käsitsi, kuna kasutame fikseeritud ID-sid
        // color.setId(null); // JPA määrab ID automaatselt
        color.setName(request.getName());
        color.setR(request.getR());
        color.setG(request.getG());
        color.setB(request.getB());
        color.setHex(toHex(request.getR(), request.getG(), request.getB()));
        return color;
    }
    
    public String toHex(Short r, Short g, Short b) {
        return String.format("#%02X%02X%02X", r, g, b);
    }
}
