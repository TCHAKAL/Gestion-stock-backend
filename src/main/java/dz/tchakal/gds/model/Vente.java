package dz.tchakal.gds.model;

import dz.tchakal.gds.dto.LigneVenteDto;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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

    @Column(name = "id_entreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "vente")
    private List<LigneVente> ligneVente;

}
