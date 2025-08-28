package ee.bcs.colors.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "color")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", nullable = false, length = 64)
    private String name;
    
    @Column(name = "r", nullable = false)
    private Short r;
    
    @Column(name = "g", nullable = false)
    private Short g;
    
    @Column(name = "b", nullable = false)
    private Short b;
    
    @Column(name = "hex", length = 7)
    private String hex;
}
