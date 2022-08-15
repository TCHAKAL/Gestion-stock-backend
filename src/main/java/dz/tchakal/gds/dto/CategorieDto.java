package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.Categorie;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
public class CategorieDto {

    private Integer id;

    private String code;

    private String designation;

    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategorieDto fromEntity(Categorie categorie) {
        if (categorie == null) {
            //TODO throw an exception
            return null;
        }
        return CategorieDto.builder()
                .id(categorie.getId())
                .code(categorie.getCode())
                .designation(categorie.getDesignation())
                .build();
    }
    public static Categorie toEntity(CategorieDto categorieDto) {
        if (categorieDto == null) {
            //TODO throw an exception
            return null;
        }
        return Categorie.builder()
                .id(categorieDto.getId())
                .code(categorieDto.getCode())
                .designation(categorieDto.getDesignation())
                .build();
    }
}