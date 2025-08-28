package ee.bcs.colors.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixedColorDto {
    private Integer id;
    private ColorDto color1;
    private ColorDto color2;
    private Short resultR;
    private Short resultG;
    private Short resultB;
    private String name;
    private String hex;
}
