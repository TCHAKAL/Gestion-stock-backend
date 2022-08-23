package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.EntrepriseDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDto entrepriseDto) {
        List<String> errors = new ArrayList<>();

        if (entrepriseDto != null) {
            if (!StringUtils.hasLength(entrepriseDto.getNom())) {
                errors.add(StaticUtil.NOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(entrepriseDto.getDescription())) {
                errors.add(StaticUtil.DESCRIPTION_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(entrepriseDto.getCodeFiscal())) {
                errors.add(StaticUtil.CODE_FISCALE_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(entrepriseDto.getEmail())) {
                errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(entrepriseDto.getTelephone())) {
                errors.add(StaticUtil.TELEPHONE_OBLIGATOIRE);
            }
            errors.addAll(AdresseValidator.validate(entrepriseDto.getAdresse()));
        } else {
            errors.add(StaticUtil.ENTITE_NULL);
            errors.add(StaticUtil.NOM_OBLIGATOIRE);
            errors.add(StaticUtil.DESCRIPTION_OBLIGATOIRE);
            errors.add(StaticUtil.CODE_FISCALE_OBLIGATOIRE);
            errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            errors.add(StaticUtil.TELEPHONE_OBLIGATOIRE);
            errors.addAll(AdresseValidator.validate(null));
        }

        return errors;
    }
}
