package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.LigneVenteDto;
import dz.tchakal.gds.util.StaticUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LigneVenteValidator {

    public static List<String> validate(LigneVenteDto ligneVenteDto){
        List<String> errors = new ArrayList<>();

        if(ligneVenteDto!=null ){
            if(ligneVenteDto.getVente()==null){
                errors.add(StaticUtil.VENTE_OBLIGATOIRE);
            }
            if(ligneVenteDto.getQuantite()==null || BigDecimal.ZERO.compareTo(ligneVenteDto.getQuantite())==0){
                errors.add(StaticUtil.QUANTITE_OBLIGATOIRE);
            }
        }else{
            errors.add(StaticUtil.ENTITE_NULL);
        }

        return errors;
    }
}
