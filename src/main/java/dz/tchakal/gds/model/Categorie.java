package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "categorie")
@Entity
public class Categorie extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "designation", nullable = false)
    private String designation;

    @OneToMany(mappedBy = "categorie")
    private List<Article> articles;

    @Column(name = "entreprise")
    private Integer entreprise;

}
