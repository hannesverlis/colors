package ee.bcs.colors.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateColorRequest {
    
    @NotBlank(message = "Värvi nimi on kohustuslik")
    @Size(max = 64, message = "Värvi nimi ei tohi olla pikem kui 64 tähemärki")
    private String name;
    
    @NotNull(message = "Punane komponent on kohustuslik")
    @Min(value = 0, message = "Punane komponent peab olema 0-255 vahel")
    @Max(value = 255, message = "Punane komponent peab olema 0-255 vahel")
    private Short r;
    
    @NotNull(message = "Roheline komponent on kohustuslik")
    @Min(value = 0, message = "Roheline komponent peab olema 0-255 vahel")
    @Max(value = 255, message = "Roheline komponent peab olema 0-255 vahel")
    private Short g;
    
    @NotNull(message = "Sinine komponent on kohustuslik")
    @Min(value = 0, message = "Sinine komponent peab olema 0-255 vahel")
    @Max(value = 255, message = "Sinine komponent peab olema 0-255 vahel")
    private Short b;
}
