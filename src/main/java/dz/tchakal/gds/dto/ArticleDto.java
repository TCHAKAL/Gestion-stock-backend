package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Categorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    private Integer entreprise;


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
                .entreprise(article.getEntreprise())
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
                .entreprise(articleDto.getEntreprise())
                .build();
    }

}
