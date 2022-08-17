package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.model.Entreprise;
import dz.tchakal.gds.model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseDto {
    private Integer id;

    private String nom;

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
                .build();
    }
}
