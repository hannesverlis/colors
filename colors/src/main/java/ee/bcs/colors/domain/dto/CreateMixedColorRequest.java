package ee.bcs.colors.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateMixedColorRequest {
    
    @NotNull(message = "Esimene värv on kohustuslik")
                @Min(value = 0, message = "Värvi ID peab olema 0 või suurem")
            private Integer color1Id;

            @NotNull(message = "Teine värv on kohustuslik")
            @Min(value = 0, message = "Värvi ID peab olema 0 või suurem")
            private Integer color2Id;
    
    @Size(max = 64, message = "Värvi nimi ei tohi olla pikem kui 64 tähemärki")
    private String name;
}
