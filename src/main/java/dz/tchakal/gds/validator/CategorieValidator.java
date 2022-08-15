package dz.tchakal.gds.validator;

import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.model.Categorie;
import dz.tchakal.gds.utils.StaticUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategorieValidator {

    public static List<String> validate(CategorieDto categorieDto){
        List<String> errors = new ArrayList<>();

        if(categorieDto!=null){
            if(!StringUtils.hasLength(categorieDto.getCode())){
                errors.add(StaticUtil.CODE_OBLIGATOIRE);
            }
        }else{
            errors.add(StaticUtil.ENTITE_NULL);
        }
        return errors;
    }
}
