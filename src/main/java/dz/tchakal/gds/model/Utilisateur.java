package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "utilisateur")
@Entity
public class Utilisateur extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "email")
    private String email;

    @Column(name = "date_naissance")
    private Instant dateNaissance;

    @Column(name = "mot_passe")
    private String motPasse;

    @Column(name = "adresse")
    private Adresse adresse;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "entreprise")
    private Entreprise entreprise;

    @ToString.Exclude
    @OneToMany(mappedBy = "utilisateur")
    private List<Role> roles;

}
