package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Builder
public class LigneCommandeClientDto {

    private ArticleDto article;

    private CommandeClientDto commandeClient;

}
