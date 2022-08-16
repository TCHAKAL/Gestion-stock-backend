package dz.tchakal.gds.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dz.tchakal.gds.model.AbstractEntity;
import dz.tchakal.gds.model.Client;
import dz.tchakal.gds.model.Fournisseur;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class FournisseurDto{

    private Integer id;

    private String nom;

    private String prenom;

    private String mail;

    private String telephone;

    private AdresseDto adresse;

    private String photo;

    private Integer entreprise;

    private List<CommandeFournisseurDto> commandeFournisseurs;


    public static FournisseurDto fromEntity(Fournisseur fournisseur) {
        if (fournisseur == null) {
            //TODO throw an exception
            return null;
        }
        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .mail(fournisseur.getMail())
                .telephone(fournisseur.getTelephone())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .photo(fournisseur.getPhoto())
                .entreprise(fournisseur.getEntreprise())
                .build();
    }
    public static Fournisseur toEntity(FournisseurDto fournisseurDto) {
        if (fournisseurDto == null) {
            //TODO throw an exception
            return null;
        }
        return Fournisseur.builder()
                .id(fournisseurDto.getId())
                .nom(fournisseurDto.getNom())
                .prenom(fournisseurDto.getPrenom())
                .mail(fournisseurDto.getMail())
                .telephone(fournisseurDto.getTelephone())
                .adresse(AdresseDto.toEntity(fournisseurDto.getAdresse()))
                .photo(fournisseurDto.getPhoto())
                .entreprise(fournisseurDto.getEntreprise())
                .build();
    }
}
