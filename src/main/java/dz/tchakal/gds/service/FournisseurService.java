package dz.tchakal.gds.service;

import dz.tchakal.gds.dto.FournisseurDto;

import java.util.List;

public interface FournisseurService {
    FournisseurDto save(FournisseurDto FournisseurDto);

    FournisseurDto findById(Integer id);

    //FournisseurDto findByNomAndPrenom(String nom,String prenom);

    FournisseurDto findByMail(String mail);

    List<FournisseurDto> findAll();

    void delete(Integer id);
}
