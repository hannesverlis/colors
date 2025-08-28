package ee.bcs.colors.domain.service;

import ee.bcs.colors.domain.dto.ColorDto;
import ee.bcs.colors.domain.dto.CreateColorRequest;
import ee.bcs.colors.domain.entity.Color;
import ee.bcs.colors.infrastructure.mapper.ColorMapper;
import ee.bcs.colors.infrastructure.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ColorService {
    
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;
    
    public List<ColorDto> getAllColors() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public ColorDto getColorById(Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Värv ID-ga " + id + " ei leitud"));
        return colorMapper.toDto(color);
    }
    
    public ColorDto createColor(CreateColorRequest request) {
        if (colorRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Värv nimega '" + request.getName() + "' on juba olemas");
        }
        
        Color color = colorMapper.toEntity(request);
        Color savedColor = colorRepository.save(color);
        return colorMapper.toDto(savedColor);
    }
    
    public ColorDto updateColor(Integer id, CreateColorRequest request) {
        Color existingColor = colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Värv ID-ga " + id + " ei leitud"));
        
        if (!existingColor.getName().equalsIgnoreCase(request.getName()) && 
            colorRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Värv nimega '" + request.getName() + "' on juba olemas");
        }
        
        existingColor.setName(request.getName());
        existingColor.setR(request.getR());
        existingColor.setG(request.getG());
        existingColor.setB(request.getB());
        existingColor.setHex(colorMapper.toHex(request.getR(), request.getG(), request.getB()));
        
        Color savedColor = colorRepository.save(existingColor);
        return colorMapper.toDto(savedColor);
    }
    
    public void deleteColor(Integer id) {
        if (!colorRepository.existsById(id)) {
            throw new RuntimeException("Värv ID-ga " + id + " ei leitud");
        }
        colorRepository.deleteById(id);
    }
    
    // Abimeetod teiste teenuste jaoks
    public Color getColorEntityById(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Värv ID-ga " + id + " ei leitud"));
    }
}
