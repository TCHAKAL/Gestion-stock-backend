package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "entreprise")
@Entity
public class Entreprise extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "code_fiscal")
    private String codeFiscal;

    @Column(name = "photo")
    private String photo;

    @Column(name = "adresse")
    private Adresse adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "site_web")
    private String siteWeb;


    @ToString.Exclude //pour eviter la boucle infinie de java.lang.StackOverflowError: null
    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs;
}
