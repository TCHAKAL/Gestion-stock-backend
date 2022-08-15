package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Adresse;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
public class AdresseDto {

    private Integer id;

    private String adresse1;

    private String adresse2;

    private String ville;

    private String codePostale;

    private String pays;

    public static AdresseDto fromEntity(Adresse adresse){
        if(adresse==null){
            return null;
        }
        return AdresseDto.builder()
                //.id(adresse.getId())
                .adresse1(adresse.getAdresse1())
                .adresse2(adresse.getAdresse2())
                .codePostale(adresse.getCodePostale())
                .pays(adresse.getPays())
                .build();
    }
    public static Adresse toEntity(AdresseDto adresseDto){
        if(adresseDto==null){
            return null;
        }
        return Adresse.builder()
               // .id(adresseDto.getId())
                .adresse1(adresseDto.getAdresse1())
                .adresse2(adresseDto.getAdresse2())
                .codePostale(adresseDto.getCodePostale())
                .pays(adresseDto.getPays())
                .build();
    }
}
