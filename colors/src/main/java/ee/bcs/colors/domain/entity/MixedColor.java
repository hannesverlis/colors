package ee.bcs.colors.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "mixed_color")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MixedColor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color1_id", nullable = false)
    private Color color1;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "color2_id", nullable = false)
    private Color color2;
    
    @Column(name = "result_r", nullable = false)
    private Short resultR;
    
    @Column(name = "result_g", nullable = false)
    private Short resultG;
    
    @Column(name = "result_b", nullable = false)
    private Short resultB;
    
    @Column(name = "name", length = 64)
    private String name;
    
    @Column(name = "hex", length = 7)
    private String hex;
}
