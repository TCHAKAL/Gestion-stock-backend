package dz.tchakal.gds.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
public class AdresseDto {

    private String adresse1;

    private String adresse2;

    private String ville;

    private String codePostale;

    private String pays;
}
