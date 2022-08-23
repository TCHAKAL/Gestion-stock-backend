package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.Fournisseur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FournisseurDto{

    private Integer id;

    private String nom;

    private String prenom;

    private String mail;

    private String telephone;

    private AdresseDto adresse;

    private String photo;

     private Integer idEntreprise;

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
                .idEntreprise(fournisseur.getIdEntreprise())
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
                .idEntreprise(fournisseurDto.getIdEntreprise())
                .build();
    }
}
