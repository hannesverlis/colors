package ee.bcs.colors.infrastructure.mapper;

import ee.bcs.colors.domain.dto.MixedColorDto;
import ee.bcs.colors.domain.entity.MixedColor;
import org.springframework.stereotype.Component;

@Component
public class MixedColorMapper {
    
    private final ColorMapper colorMapper;
    
    public MixedColorMapper(ColorMapper colorMapper) {
        this.colorMapper = colorMapper;
    }
    
    public MixedColorDto toDto(MixedColor mixedColor) {
        if (mixedColor == null) return null;
        
        return new MixedColorDto(
            mixedColor.getId(),
            colorMapper.toDto(mixedColor.getColor1()),
            colorMapper.toDto(mixedColor.getColor2()),
            mixedColor.getResultR(),
            mixedColor.getResultG(),
            mixedColor.getResultB(),
            mixedColor.getName(),
            mixedColor.getHex()
        );
    }
}
