package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "vente")
@Entity
public class Vente extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "code")
    private  String code;
    @Column(name = "date_vente")
    private Instant dateVente;
    @Column(name = "commentaire")
    private  String commentaire;
}
