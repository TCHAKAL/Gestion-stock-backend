package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CommandeFournisseurDto {

    private String code;

    private Instant dateCommande;

    private FournisseurDto fournisseur;

    private List<LigneCommandeFournisseurDto> ligneCommandeFournisseurs;
}
