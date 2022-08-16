package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.EntrepriseDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseValidator {

    public static List<String> validate(EntrepriseDto entrepriseDto){
        List<String> errors = new ArrayList<>();

        if(entrepriseDto!=null ){
            if(!StringUtils.hasLength(entrepriseDto.getNom())){
                errors.add(StaticUtil.NOM_OBLIGATOIRE);
            }

        }else{
            errors.add(StaticUtil.ENTITE_NULL);
        }

        return errors;
    }
}
