package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Categorie;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Builder
@Data

public class ArticleDto {

    private String code;

    private String designation;

    private BigDecimal prixUnitaireHt;

    private BigDecimal prixUnitaireTtc;

    private BigDecimal tauxTva;

    private String photo;

    private CategorieDto categorie;

}
