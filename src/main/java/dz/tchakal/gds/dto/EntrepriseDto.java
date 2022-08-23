package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.Adresse;
import dz.tchakal.gds.model.Entreprise;
import dz.tchakal.gds.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseDto {
    private Integer id;

    private String nom;

    private String email;

    private String photo;

    private Adresse adresse;

    @JsonIgnore
    private List<Utilisateur> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise) {
        if (entreprise == null) {
            //TODO throw an exception
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .email(entreprise.getEmail())
                .adresse(entreprise.getAdresse())
                .photo(entreprise.getPhoto())
                .build();
    }
    public static Entreprise toEntity(EntrepriseDto entrepriseDto) {
        if (entrepriseDto == null) {
            //TODO throw an exception
            return null;
        }
        return Entreprise.builder()
                .id(entrepriseDto.getId())
                .nom(entrepriseDto.getNom())
                .email(entrepriseDto.getEmail())
                .adresse(entrepriseDto.getAdresse())
                .photo(entrepriseDto.getPhoto())
                .build();
    }
}
