package ee.bcs.colors.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {
    private Integer id;
    private String name;
    private Short r;
    private Short g;
    private Short b;
    private String hex;
}
