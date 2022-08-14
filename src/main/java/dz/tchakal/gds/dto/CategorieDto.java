package dz.tchakal.gds.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
@Data
@Builder
public class CategorieDto {
    private String code;

    private String designation;

    private List<ArticleDto> articles;
}
