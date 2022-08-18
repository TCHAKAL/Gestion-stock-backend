package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise,Integer> {


    Entreprise findByNom(String nom);
}
