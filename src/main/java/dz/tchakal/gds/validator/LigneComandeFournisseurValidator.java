package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.LigneCommandeClientDto;
import dz.tchakal.gds.dto.LigneCommandeFournisseurDto;
import dz.tchakal.gds.utils.StaticUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LigneComandeFournisseurValidator {

    public static List<String> validate(LigneCommandeFournisseurDto commandeFournisseurDto){
        List<String> errors = new ArrayList<>();

        if(commandeFournisseurDto!=null ){
            if(commandeFournisseurDto.getArticle()==null){
                errors.add(StaticUtil.ARTICLE_OBLIGATOIRE);
            }
            if(commandeFournisseurDto.getQuantite()==null || BigDecimal.ZERO.compareTo(commandeFournisseurDto.getQuantite())==0){
                errors.add(StaticUtil.QUANTITE_OBLIGATOIRE);
            }
            if(commandeFournisseurDto.getPrixUnitaire()==null || BigDecimal.ZERO.compareTo(commandeFournisseurDto.getPrixUnitaire())==0){
                errors.add(StaticUtil.PRIX_UNITAIRE_OBLIGATOIRE);
            }
        }else{
            errors.add(StaticUtil.ENTITE_NULL);
        }

        return errors;
    }
}
