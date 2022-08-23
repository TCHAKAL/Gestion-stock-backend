package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Integer id;

    private String code;

    private String designation;

    private BigDecimal prixUnitaireHt;

    private BigDecimal prixUnitaireTtc;

    private BigDecimal tauxTva;

    private String photo;

    private CategorieDto categorie;

     private Integer idEntreprise;


    public static ArticleDto fromEntity(Article article){
        if(article==null){
            return null;
        }
        return ArticleDto.builder()
                .id(article.getId())
                .code(article.getCode())
                .designation(article.getDesignation())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .tauxTva(article.getTauxTva())
                .photo(article.getPhoto())
                .idEntreprise(article.getIdEntreprise())
                .build();
    }
 public static Article toEntity(ArticleDto articleDto){
        if(articleDto==null){
            return null;
        }
        return Article.builder()
                .id(articleDto.getId())
                .code(articleDto.getCode())
                .designation(articleDto.getDesignation())
                .prixUnitaireHt(articleDto.getPrixUnitaireHt())
                .prixUnitaireTtc(articleDto.getPrixUnitaireTtc())
                .tauxTva(articleDto.getTauxTva())
                .photo(articleDto.getPhoto())
                .idEntreprise(articleDto.getIdEntreprise())
                .build();
    }

}
