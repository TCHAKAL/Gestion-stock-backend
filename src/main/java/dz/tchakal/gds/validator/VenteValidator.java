package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.CommandeClientDto;
import dz.tchakal.gds.dto.VenteDto;
import dz.tchakal.gds.utils.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VenteValidator {

    public static List<String> validate(VenteDto venteDto){
        List<String> errors = new ArrayList<>();

        if(venteDto!=null ){
            if(!StringUtils.hasLength(venteDto.getCode())){
                errors.add(StaticUtil.CODE_OBLIGATOIRE);
            }
            if(venteDto.getDateVente()==null){
                errors.add(StaticUtil.DATE_OBLIGATOIRE);
            }
        }else{
            errors.add(StaticUtil.ENTITE_NULL);
        }

        return errors;
    }
}
