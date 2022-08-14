package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class FournisseurDto{


    private String nom;

    private String prenom;

    private String mail;

    private String telephone;

    private AdresseDto adresse;

    private String photo;

    private List<CommandeFournisseurDto> commandeFournisseurs;
}
