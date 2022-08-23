package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.LigneCommandeClient;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LigneCommandeClientDto {

    private Integer id;

    private ArticleDto article;

    private CommandeClientDto commandeClient;

    private BigDecimal quantite;

    private BigDecimal prixUnitaire;



    public static LigneCommandeClientDto fromEntity(LigneCommandeClient ligneCommandeClient) {
        if (ligneCommandeClient == null) {
            //TODO throw an exception
            return null;
        }
        return LigneCommandeClientDto.builder()
                .id(ligneCommandeClient.getId())
                .article(ArticleDto.fromEntity(ligneCommandeClient.getArticle()))
                .commandeClient(CommandeClientDto.fromEntity(ligneCommandeClient.getCommandeClient()))
                .quantite(ligneCommandeClient.getQuantite())
                .prixUnitaire(ligneCommandeClient.getPrixUnitaire())
                .build();
    }

    public static LigneCommandeClient toEntity(LigneCommandeClientDto ligneCommandeClientDto) {
        if (ligneCommandeClientDto == null) {
            //TODO throw an exception
            return null;
        }
        return LigneCommandeClient.builder()
                .id(ligneCommandeClientDto.getId())
                .article(ArticleDto.toEntity(ligneCommandeClientDto.getArticle()))
                .commandeClient(CommandeClientDto.toEntity(ligneCommandeClientDto.getCommandeClient()))
                .quantite(ligneCommandeClientDto.getQuantite())
                .prixUnitaire(ligneCommandeClientDto.getPrixUnitaire())
                .build();
    }
}
