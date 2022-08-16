package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.AbstractEntity;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.model.CommandeClient;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class CommandeClientDto {

    private Integer id;

    private String code;

    private Date dateCommande;

    private ClientDto client;

    private List<LigneCommandeClientDto> ligneCommandeClients;

    private Integer entreprise;



    public static CommandeClientDto fromEntity(CommandeClient commandeClient) {
        if (commandeClient == null) {
            //TODO throw an exception
            return null;
        }
        return CommandeClientDto.builder()
                .id(commandeClient.getId())
                .code(commandeClient.getCode())
                .dateCommande(commandeClient.getDateCommande())
                .client(ClientDto.fromEntity(commandeClient.getClient()))
                .entreprise(commandeClient.getEntreprise())
                .build();
    }
    public static CommandeClient toEntity(CommandeClientDto commandeClientDto) {
        if (commandeClientDto == null) {
            //TODO throw an exception
            return null;
        }
        return CommandeClient.builder()
                .id(commandeClientDto.getId())
                .code(commandeClientDto.getCode())
                .dateCommande(commandeClientDto.getDateCommande())
                .client(ClientDto.toEntity(commandeClientDto.getClient()))
                .entreprise(commandeClientDto.getEntreprise())
                .build();
    }
}
