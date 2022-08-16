package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.CommandeClientDto;
import dz.tchakal.gds.util.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CommandeClientValidator {

    public static List<String> validate(CommandeClientDto commandeClientDto){
        List<String> errors = new ArrayList<>();

        if(commandeClientDto!=null ){
            if(!StringUtils.hasLength(commandeClientDto.getCode())){
                errors.add(StaticUtil.CODE_OBLIGATOIRE);
            }
            if(commandeClientDto.getDateCommande()==null){
                errors.add(StaticUtil.DATE_COMMANDE_OBLIGATOIRE);
            }
            if(commandeClientDto.getClient()==null){
                errors.add(StaticUtil.CLIENT_OBLIGATOIRE);
            }
        }else{
            errors.add(StaticUtil.ENTITE_NULL);
        }

        return errors;
    }
}
