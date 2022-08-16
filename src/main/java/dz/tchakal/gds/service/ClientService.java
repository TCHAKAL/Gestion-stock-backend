package dz.tchakal.gds.service;

import dz.tchakal.gds.dto.ClientDto;
import dz.tchakal.gds.dto.FournisseurDto;

import java.util.List;

public interface ClientService {
    ClientDto save(ClientDto ClientDto);

    ClientDto findById(Integer id);

   // ClientDto findByNomAndPrenom(String nom,String prenom);

    ClientDto findByMail(String mail);

    List<ClientDto> findAll();

    void delete(Integer id);
}
