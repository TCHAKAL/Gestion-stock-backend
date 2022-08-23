package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDto {

    private Integer id;

    private String nom;

    private String prenom;

    private String email;

    private Instant dateNaissance;

    private String motPasse;

    private AdresseDto adresse;

    private String photo;

    private EntrepriseDto entreprise;

    private List<RoleDto> roles;


    public static UtilisateurDto fromEntity(Utilisateur utilisateur) {
        if (utilisateur == null) {
            //TODO throw an exception
            return null;
        }
        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .dateNaissance(utilisateur.getDateNaissance())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .email(utilisateur.getEmail())
                .motPasse(utilisateur.getMotPasse())
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .build();
    }
    public static Utilisateur toEntity(UtilisateurDto utilisateurDto) {
        if (utilisateurDto == null) {
            //TODO throw an exception
            return null;
        }
        return Utilisateur.builder()
                .id(utilisateurDto.getId())
                .nom(utilisateurDto.getNom())
                .prenom(utilisateurDto.getPrenom())
                .dateNaissance(utilisateurDto.getDateNaissance())
                .adresse(AdresseDto.toEntity(utilisateurDto.getAdresse()))
                .email(utilisateurDto.getEmail())
                .motPasse(utilisateurDto.getMotPasse())
                .photo(utilisateurDto.getPhoto())
                .entreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()))
                .build();
    }

}
