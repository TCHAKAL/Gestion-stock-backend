package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CommandeClientDto {

    private String code;

    private Date dateCommande;

    private ClientDto client;

    private List<LigneCommandeClientDto> ligneCommandeClients;

}
