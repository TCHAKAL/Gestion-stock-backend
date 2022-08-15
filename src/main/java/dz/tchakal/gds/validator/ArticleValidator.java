package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.ArticleDto;
import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.utils.StaticUtil;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArticleValidator {

    public static List<String> validate(ArticleDto articleDto) {
        List<String> errors = new ArrayList<>();

        if (articleDto == null) {
            if (!StringUtils.hasLength(articleDto.getCode())) {
                errors.add(StaticUtil.CODE_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(articleDto.getDesignation())) {
                errors.add(StaticUtil.DESIGNATION_OBLIGATOIRE);
            }
            if (articleDto.getPrixUnitaireHt()==null || BigDecimal.ZERO.compareTo(articleDto.getPrixUnitaireHt())==0 ) {
                errors.add(StaticUtil.PRIX_UNITAIRE_HT_OBLIGATOIRE);
            }
            if (articleDto.getPrixUnitaireTtc()==null || BigDecimal.ZERO.compareTo(articleDto.getPrixUnitaireTtc())==0 ) {
                errors.add(StaticUtil.PRIX_UNITAIRE_TTC_OBLIGATOIRE);
            }
            if (articleDto.getCategorie()==null) {
                errors.add(StaticUtil.CATEGORIE_OBLIGATOIRE);
            }
        } else {
            errors.add(StaticUtil.ENTITE_NULL);
        }
        return errors;
    }
}
