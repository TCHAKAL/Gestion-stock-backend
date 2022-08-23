package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.FournisseurDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto fournisseurDto) {
        List<String> errors = new ArrayList<>();

        if (fournisseurDto != null) {
            if (!StringUtils.hasLength(fournisseurDto.getNom())) {
                errors.add(StaticUtil.NOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(fournisseurDto.getPrenom())) {
                errors.add(StaticUtil.PRENOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(fournisseurDto.getMail())) {
                errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(fournisseurDto.getTelephone())) {
                errors.add(StaticUtil.TELEPHONE_OBLIGATOIRE);
            }
            errors.addAll(AdresseValidator.validate(fournisseurDto.getAdresse()));
        } else {
            errors.add(StaticUtil.ENTITE_NULL);
            errors.add(StaticUtil.NOM_OBLIGATOIRE);
            errors.add(StaticUtil.PRENOM_OBLIGATOIRE);
            errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            errors.add(StaticUtil.TELEPHONE_OBLIGATOIRE);
            errors.addAll(AdresseValidator.validate(null));
        }
        return errors;
    }
}
