package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Vente;
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
public class VenteDto {

    private Integer id;

    private String code;

    private Instant dateVente;

    private String commentaire;

    private Integer entreprise;

    private List<LigneVenteDto> ligneVente;

    public static VenteDto fromEntity(Vente vente) {
        if (vente == null) {
            //TODO throw an exception
            return null;
        }
        return VenteDto.builder()
                .id(vente.getId())
                .code(vente.getCode())
                .dateVente(vente.getDateVente())
                .commentaire(vente.getCommentaire())
                .entreprise(vente.getEntreprise())
                .build();
    }

    public static Vente toEntity(VenteDto venteDto) {
        if (venteDto == null) {
            //TODO throw an exception
            return null;
        }
        return Vente.builder()
                .id(venteDto.getId())
                .code(venteDto.getCode())
                .dateVente(venteDto.getDateVente())
                .commentaire(venteDto.getCommentaire())
                .entreprise(venteDto.getEntreprise())
                .build();
    }

}
