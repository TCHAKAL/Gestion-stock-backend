package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.*;
import dz.tchakal.gds.model.enumeration.SourceMvtStock;
import dz.tchakal.gds.model.enumeration.TypeMvt;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MvtStockDto {

    private Integer id;

    private ArticleDto article;

    private BigDecimal quantite;

    private Instant dateMvt;

    private TypeMvt typeMvt;

    private SourceMvtStock sourceMvtStock;

     private Integer idEntreprise;

    public static MvtStockDto fromEntity(MvtStock mvtStock) {
        if (mvtStock == null) {
            //TODO throw an exception
            return null;
        }
        return MvtStockDto.builder()
                .id(mvtStock.getId())
                .article(ArticleDto.fromEntity(mvtStock.getArticle()))
                .quantite(mvtStock.getQuantite())
                .dateMvt(mvtStock.getDateMvt())
                .typeMvt(mvtStock.getTypeMvt())
                .sourceMvtStock(mvtStock.getSourceMvtStock())
                .idEntreprise(mvtStock.getIdEntreprise())
                .build();
    }

    public static MvtStock toEntity(MvtStockDto mvtStockDto) {
        if (mvtStockDto == null) {
            //TODO throw an exception
            return null;
        }
        return MvtStock.builder()
                .id(mvtStockDto.getId())
                .article(ArticleDto.toEntity(mvtStockDto.getArticle()))
                .quantite(mvtStockDto.getQuantite())
                .dateMvt(mvtStockDto.getDateMvt())
                .typeMvt(mvtStockDto.getTypeMvt())
                .sourceMvtStock(mvtStockDto.getSourceMvtStock())
                .idEntreprise(mvtStockDto.getIdEntreprise())
                .build();
    }

}
