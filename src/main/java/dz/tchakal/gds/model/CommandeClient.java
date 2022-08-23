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
@Table(name = "commande_client")
@Entity
public class CommandeClient extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="code")
    private String code;

    @Column(name="date_commande")
    private Instant dateCommande;

    @Column(name="etat_commande")
    private EtatCommande etatCommande;
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToMany(mappedBy = "commandeClient")
    private List<LigneCommandeClient> ligneCommandeClients;

    @Column(name = "entreprise")
    private Integer entreprise;
}
