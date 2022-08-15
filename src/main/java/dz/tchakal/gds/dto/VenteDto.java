package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.model.Vente;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Builder
public class VenteDto {

    private Integer id;

    private  String code;

    private Instant dateVente;

    private  String commentaire;


    public static VenteDto fromEntity(Vente vente) {
        if (vente == null) {
            //TODO throw an exception
            return null;
        }
        return VenteDto.builder()
                .id(vente.getId())
                .code(vente.getCode())
                .dateVente(vente.getDateVente())
                .commentaire(vente.getCommentaire())
                .build();
    }
    public static Vente toEntity(VenteDto venteDto) {
        if (venteDto == null) {
            //TODO throw an exception
            return null;
        }
        return Vente.builder()
                .id(venteDto.getId())
                .code(venteDto.getCode())
                .dateVente(venteDto.getDateVente())
                .commentaire(venteDto.getCommentaire())
                .build();
    }

}
