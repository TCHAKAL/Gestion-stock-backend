package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.ClientDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    public static List<String> validate(ClientDto clientDto) {
        List<String> errors = new ArrayList<>();

        if (clientDto != null) {
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
            errors.addAll(AdresseValidator.validate(clientDto.getAdresse()));
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
