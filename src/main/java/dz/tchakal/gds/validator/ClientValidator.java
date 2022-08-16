package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.ClientDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientDto clientDto) {
        List<String> errors = new ArrayList<>();

        if (clientDto == null) {
            if (!StringUtils.hasLength(clientDto.getNom())) {
                errors.add(StaticUtil.NOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(clientDto.getPrenom())) {
                errors.add(StaticUtil.PRENOM_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(clientDto.getMail())) {
                errors.add(StaticUtil.EMAIL_OBLIGATOIRE);
            }
            if (!StringUtils.hasLength(clientDto.getTelephone())) {
                errors.add(StaticUtil.TELEPHONE_OBLIGATOIRE);
            }
            if (clientDto.getAdresse() == null) {
                errors.add(StaticUtil.ADRESSE_OBLIGATOIRE);
            } else {
                if (clientDto.getAdresse().getAdresse1() == null || !StringUtils.hasLength(clientDto.getAdresse().getAdresse1())) {
                    errors.add(StaticUtil.ADRESSE1_OBLIGATOIRE);
                }
                if (clientDto.getAdresse().getVille() == null || !StringUtils.hasLength(clientDto.getAdresse().getVille())) {
                    errors.add(StaticUtil.VILLE_OBLIGATOIRE);
                }
                if (clientDto.getAdresse().getCodePostale() == null || !StringUtils.hasLength(clientDto.getAdresse().getCodePostale())) {
                    errors.add(StaticUtil.CODE_POSTALE_OBLIGATOIRE);
                }
                if (clientDto.getAdresse().getPays() == null || !StringUtils.hasLength(clientDto.getAdresse().getPays())) {
                    errors.add(StaticUtil.PAYS_OBLIGATOIRE);
                }
            }


        } else {
            errors.add(StaticUtil.ENTITE_NULL);
        }
        return errors;
    }
}
