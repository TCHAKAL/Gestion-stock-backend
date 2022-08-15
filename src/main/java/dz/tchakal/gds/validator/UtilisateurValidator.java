package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.UtilisateurDto;
import dz.tchakal.gds.utils.StaticUtil;
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
                    errors.add(StaticUtil.AGE_invalide);
                }
            }
            if (utilisateurDto.getAdresse() == null) {
                errors.add(StaticUtil.ADRESSE_OBLIGATOIRE);
            } else {
                if (utilisateurDto.getAdresse().getAdresse1() == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getAdresse1())) {
                    errors.add(StaticUtil.ADRESSE1_OBLIGATOIRE);
                }
                if (utilisateurDto.getAdresse().getVille() == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getVille())) {
                    errors.add(StaticUtil.VILLE_OBLIGATOIRE);
                }
                if (utilisateurDto.getAdresse().getCodePostale() == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getCodePostale())) {
                    errors.add(StaticUtil.CODE_POSTALE_OBLIGATOIRE);
                }
                if (utilisateurDto.getAdresse().getPays() == null || !StringUtils.hasLength(utilisateurDto.getAdresse().getPays())) {
                    errors.add(StaticUtil.PAYS_OBLIGATOIRE);
                }
            }
        } else {
            errors.add(StaticUtil.ENTITE_NULL);
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
