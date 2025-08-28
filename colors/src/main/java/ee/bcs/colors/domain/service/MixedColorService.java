package ee.bcs.colors.domain.service;

import ee.bcs.colors.domain.dto.MixedColorDto;
import ee.bcs.colors.domain.dto.CreateMixedColorRequest;
import ee.bcs.colors.domain.entity.MixedColor;
import ee.bcs.colors.infrastructure.mapper.MixedColorMapper;
import ee.bcs.colors.infrastructure.repository.MixedColorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MixedColorService {
    
    private final MixedColorRepository mixedColorRepository;
    private final ColorService colorService;
    private final MixedColorMapper mixedColorMapper;
    
    public List<MixedColorDto> getAllMixedColors() {
        return mixedColorRepository.findAll()
                .stream()
                .map(mixedColorMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public MixedColorDto getMixedColorById(Integer id) {
        MixedColor mixedColor = mixedColorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Segatud värv ID-ga " + id + " ei leitud"));
        return mixedColorMapper.toDto(mixedColor);
    }
    
    public MixedColorDto createMixedColor(CreateMixedColorRequest request) {
        log.info("Loon segatud värvi: color1Id={}, color2Id={}, name={}", 
                request.getColor1Id(), request.getColor2Id(), request.getName());
        
        if (request.getColor1Id().equals(request.getColor2Id())) {
            throw new RuntimeException("Ei saa segada sama värvi iseendaga");
        }
        
        // Kontrolli, kas värvid eksisteerivad
        try {
            var color1 = colorService.getColorEntityById(request.getColor1Id());
            log.info("Leitud värv 1: id={}, name={}", color1.getId(), color1.getName());
            
            var color2 = colorService.getColorEntityById(request.getColor2Id());
            log.info("Leitud värv 2: id={}, name={}", color2.getId(), color2.getName());
            
            // Kontrolli, kas selline kombinatsioon on juba olemas
            if (mixedColorRepository.findByColorPair(request.getColor1Id(), request.getColor2Id()).isPresent()) {
                throw new RuntimeException("Selline värvide kombinatsioon on juba olemas");
            }
            
            // Arvuta segatud värv (keskmine)
            short resultR = (short) ((color1.getR() + color2.getR()) / 2);
            short resultG = (short) ((color1.getG() + color2.getG()) / 2);
            short resultB = (short) ((color1.getB() + color2.getB()) / 2);
            
            String hex = String.format("#%02X%02X%02X", resultR, resultG, resultB);
            String name = request.getName() != null ? request.getName() : color1.getName() + " + " + color2.getName();
            
            log.info("Arvutatud segatud värv: RGB({},{},{}), HEX={}, name={}", 
                    resultR, resultG, resultB, hex, name);
            
            MixedColor mixedColor = new MixedColor();
            mixedColor.setColor1(color1);
            mixedColor.setColor2(color2);
            mixedColor.setResultR(resultR);
            mixedColor.setResultG(resultG);
            mixedColor.setResultB(resultB);
            mixedColor.setName(name);
            mixedColor.setHex(hex);
            
            MixedColor savedMixedColor = mixedColorRepository.save(mixedColor);
            log.info("Segatud värv salvestatud: id={}", savedMixedColor.getId());
            
            return mixedColorMapper.toDto(savedMixedColor);
            
        } catch (RuntimeException e) {
            log.error("Viga segatud värvi loomisel: {}", e.getMessage());
            throw e;
        }
    }
    
    public void deleteMixedColor(Integer id) {
        if (!mixedColorRepository.existsById(id)) {
            throw new RuntimeException("Segatud värv ID-ga " + id + " ei leitud");
        }
        mixedColorRepository.deleteById(id);
    }
    
    public List<MixedColorDto> getMixedColorsByColorId(Integer colorId) {
        return mixedColorRepository.findByColorId(colorId)
                .stream()
                .map(mixedColorMapper::toDto)
                .collect(Collectors.toList());
    }
}
