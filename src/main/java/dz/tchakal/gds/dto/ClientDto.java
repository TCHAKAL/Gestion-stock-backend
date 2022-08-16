package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.AbstractEntity;
import dz.tchakal.gds.model.Adresse;
import dz.tchakal.gds.model.Categorie;
import dz.tchakal.gds.model.Client;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class ClientDto{

    private Integer id;
    private Integer entreprise;

    private String nom;

    private String prenom;

    private String mail;

    private String telephone;

    private AdresseDto adresse;

    private String photo;

    @JsonIgnore
    private List<CommandeClientDto> commandeClients;

    public static ClientDto fromEntity(Client client) {
        if (client == null) {
            //TODO throw an exception
            return null;
        }
        return ClientDto.builder()
                .id(client.getId())
                .nom(client.getNom())
                .prenom(client.getPrenom())
                .mail(client.getMail())
                .telephone(client.getTelephone())
                .adresse(AdresseDto.fromEntity(client.getAdresse()))
                .photo(client.getPhoto())
                .entreprise(client.getEntreprise())
                .build();
    }
    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            //TODO throw an exception
            return null;
        }
        return Client.builder()
                .id(clientDto.getId())
                .nom(clientDto.getNom())
                .prenom(clientDto.getPrenom())
                .mail(clientDto.getMail())
                .telephone(clientDto.getTelephone())
                .adresse(AdresseDto.toEntity(clientDto.getAdresse()))
                .photo(clientDto.getPhoto())
                .entreprise(clientDto.getEntreprise())
                .build();
    }
}
