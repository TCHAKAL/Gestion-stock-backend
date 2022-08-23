package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UtilisateurValidator {

    public static List<String> validate(UtilisateurDto utilisateurDto) {
        List<String> errors = new ArrayList<>();

        if (utilisateurDto != null) {
            if (!StringUtils.hasLength(utilisateurDto.getNom())) {
                errors.add(StaticUtil.NOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(utilisateurDto.getPrenom())) {
                errors.add(StaticUtil.PRENOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(utilisateurDto.getEmail())) {
                errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(utilisateurDto.getMotPasse())) {
                errors.add(StaticUtil.MOT_PASSE_OBLIGATOIRE);
            }
            if (utilisateurDto.getDateNaissance() == null) {
                errors.add(StaticUtil.DATE_NAISSANCE_OBLIGATOIRE);
            } else {
                if (getAge(Date.from(utilisateurDto.getDateNaissance())) < 6) {
                    errors.add(StaticUtil.AGE_INVALIDE);
                }
            }
            errors.addAll(AdresseValidator.validate(utilisateurDto.getAdresse()));
        } else {
            errors.add(StaticUtil.ENTITE_NULL);
            errors.add(StaticUtil.DATE_OBLIGATOIRE);
            errors.add(StaticUtil.PRENOM_OBLIGATOIRE);
            errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            errors.add(StaticUtil.MOT_PASSE_OBLIGATOIRE);
            errors.add(StaticUtil.DATE_NAISSANCE_OBLIGATOIRE);
            errors.add(StaticUtil.AGE_INVALIDE);
            errors.addAll(AdresseValidator.validate(null));
        }

        return errors;
    }

    public static int getAge(Date d) {
        Calendar curr = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(d);
        int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        curr.add(Calendar.YEAR, -yeardiff);
        if (birth.after(curr)) {
            yeardiff = yeardiff - 1;
        }
        return yeardiff;
    }
}
