package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.model.LigneVente;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LigneVenteDto {

    private Integer id;

    private VenteDto vente;

    private BigDecimal quantite;

    private ArticleDto article;




    public static LigneVenteDto fromEntity(LigneVente ligneVente) {
        if (ligneVente == null) {
            //TODO throw an exception
            return null;
        }
        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .vente(VenteDto.fromEntity(ligneVente.getVente()))
                .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                .quantite(ligneVente.getQuantite())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto) {
        if (ligneVenteDto == null) {
            //TODO throw an exception
            return null;
        }
        return LigneVente.builder()
                .id(ligneVenteDto.getId())
                .vente(VenteDto.toEntity(ligneVenteDto.getVente()))
                .quantite(ligneVenteDto.getQuantite())
                .article(ArticleDto.toEntity(ligneVenteDto.getArticle()))
                .build();
    }
}
