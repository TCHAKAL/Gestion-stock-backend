package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto{

    private Integer id;
     private Integer idEntreprise;

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
                .idEntreprise(client.getIdEntreprise())
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
                .idEntreprise(clientDto.getIdEntreprise())
                .build();
    }
}
