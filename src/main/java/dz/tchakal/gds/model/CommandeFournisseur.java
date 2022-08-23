package dz.tchakal.gds.model;

import dz.tchakal.gds.model.enumeration.EtatCommande;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "commande_fournisseur")
@Entity
public class CommandeFournisseur extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "date_commande")
    private Instant dateCommande;

    @ManyToOne
    @JoinColumn(name = "fournisseur")
    private Fournisseur fournisseur;

    @Column(name="etat_commande")
    private EtatCommande etatCommande;

    @OneToMany(mappedBy = "commandeFournisseur")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;

    @Column(name = "id_entreprise")
    private Integer idEntreprise;
}
