package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.AdresseDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {

    public static List<String> validate(AdresseDto adresseDto){
        List<String> errors = new ArrayList<>();
        if (adresseDto == null) {
            errors.add(StaticUtil.ENTITE_NULL);
            errors.add(StaticUtil.ADRESSE1_OBLIGATOIRE);
            errors.add(StaticUtil.VILLE_OBLIGATOIRE);
            errors.add(StaticUtil.CODE_POSTALE_OBLIGATOIRE);
            errors.add(StaticUtil.PAYS_OBLIGATOIRE);
        } else {
            if (adresseDto.getAdresse1() == null || !StringUtils.hasLength(adresseDto.getAdresse1())) {
                errors.add(StaticUtil.ADRESSE1_OBLIGATOIRE);
            }
            if (adresseDto.getVille() == null || !StringUtils.hasLength(adresseDto.getVille())) {
                errors.add(StaticUtil.VILLE_OBLIGATOIRE);
            }
            if (adresseDto.getCodePostale() == null || !StringUtils.hasLength(adresseDto.getCodePostale())) {
                errors.add(StaticUtil.CODE_POSTALE_OBLIGATOIRE);
            }
            if (adresseDto.getPays() == null || !StringUtils.hasLength(adresseDto.getPays())) {
                errors.add(StaticUtil.PAYS_OBLIGATOIRE);
            }
        }
        return errors;
    }
}
