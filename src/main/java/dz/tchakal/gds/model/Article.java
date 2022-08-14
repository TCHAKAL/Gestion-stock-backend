package dz.tchakal.gds.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "article")
@Entity
public class Article extends AbstractEntity{

    @Column(name="code",nullable = false)
    private String code;

    @Column(name="designation",nullable = false)
    private String designation;

    @Column(name = "prix_unitaire_ht")
    private BigDecimal prixUnitaireHt;

    @Column(name = "prix_unitaire_ttc")
    private BigDecimal prixUnitaireTtc;

    @Column(name = "taux_tva")
    private BigDecimal tauxTva;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

}
