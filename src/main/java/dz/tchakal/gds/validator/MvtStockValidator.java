package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.MvtStockDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStockValidator {

    public static List<String> validate(MvtStockDto mvtStockDto) {
        List<String> errors = new ArrayList<>();

        if (mvtStockDto != null) {
            if (mvtStockDto.getArticle() == null) {
                errors.add(StaticUtil.ARTICLE_OBLIGATOIRE);
            }
            if (mvtStockDto.getQuantite() == null || BigDecimal.ZERO.compareTo(mvtStockDto.getQuantite()) == 0) {
                errors.add(StaticUtil.QUANTITE_OBLIGATOIRE);
            }
            if (mvtStockDto.getDateMvt() == null) {
                errors.add(StaticUtil.DATE_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(mvtStockDto.getTypeMvt().name())) {
                errors.add(StaticUtil.TYPE_OBLIGATOIRE);
            }

        } else {
            errors.add(StaticUtil.ENTITE_NULL);
            errors.add(StaticUtil.ARTICLE_OBLIGATOIRE);
            errors.add(StaticUtil.QUANTITE_OBLIGATOIRE);
            errors.add(StaticUtil.DATE_OBLIGATOIRE);
            errors.add(StaticUtil.TYPE_OBLIGATOIRE);
        }

        return errors;
    }
}
