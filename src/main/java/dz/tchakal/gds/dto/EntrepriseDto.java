package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.Entreprise;
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

    private String description;

    private String codeFiscal;

    private String photo;

    private AdresseDto adresse;

    private String telephone;

    private String siteWeb;

    @JsonIgnore
    private List<UtilisateurDto> utilisateurs;

    public static EntrepriseDto fromEntity(Entreprise entreprise) {
        if (entreprise == null) {
            //TODO throw an exception
            return null;
        }
        return EntrepriseDto.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .email(entreprise.getEmail())
                .description(entreprise.getDescription())
                .codeFiscal(entreprise.getCodeFiscal())
                .photo(entreprise.getPhoto())
                .adresse(AdresseDto.fromEntity(entreprise.getAdresse()))
                .telephone(entreprise.getTelephone())
                .siteWeb(entreprise.getSiteWeb())
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
                .description(entrepriseDto.getDescription())
                .codeFiscal(entrepriseDto.getCodeFiscal())
                .photo(entrepriseDto.getPhoto())
                .adresse(AdresseDto.toEntity(entrepriseDto.getAdresse()))
                .telephone(entrepriseDto.getTelephone())
                .siteWeb(entrepriseDto.getSiteWeb())
                .build();
    }
}
