package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
public class UtilisateurDto {
    private String nom;

}
