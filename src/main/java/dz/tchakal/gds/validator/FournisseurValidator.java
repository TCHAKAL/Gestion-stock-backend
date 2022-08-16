package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.FournisseurDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FournisseurValidator {

    public static List<String> validate(FournisseurDto fournisseurDto) {
        List<String> errors = new ArrayList<>();

        if (fournisseurDto == null) {
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
            if (fournisseurDto.getAdresse() == null) {
                errors.add(StaticUtil.ADRESSE_OBLIGATOIRE);
            } else {
                if (fournisseurDto.getAdresse().getAdresse1() == null || !StringUtils.hasLength(fournisseurDto.getAdresse().getAdresse1())) {
                    errors.add(StaticUtil.ADRESSE1_OBLIGATOIRE);
                }
                if (fournisseurDto.getAdresse().getVille() == null || !StringUtils.hasLength(fournisseurDto.getAdresse().getVille())) {
                    errors.add(StaticUtil.VILLE_OBLIGATOIRE);
                }
                if (fournisseurDto.getAdresse().getCodePostale() == null || !StringUtils.hasLength(fournisseurDto.getAdresse().getCodePostale())) {
                    errors.add(StaticUtil.CODE_POSTALE_OBLIGATOIRE);
                }
                if (fournisseurDto.getAdresse().getPays() == null || !StringUtils.hasLength(fournisseurDto.getAdresse().getPays())) {
                    errors.add(StaticUtil.PAYS_OBLIGATOIRE);
                }
            }


        } else {
            errors.add(StaticUtil.ENTITE_NULL);
        }
        return errors;
    }
}
