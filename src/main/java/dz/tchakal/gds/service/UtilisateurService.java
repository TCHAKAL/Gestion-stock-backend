package dz.tchakal.gds.service;

import dz.tchakal.gds.dto.UtilisateurDto;

import java.util.List;

public interface UtilisateurService {
    UtilisateurDto save(UtilisateurDto UtilisateurDto);

    UtilisateurDto findById(Integer id);

    //UtilisateurDto findByNomAndPrenom(String nom,String prenom);

    UtilisateurDto findByEmail(String email);

    UtilisateurDto findByEmailAndMotPasse(String email,String motPasse);

    List<UtilisateurDto> findAll();

    void delete(Integer id);
}
