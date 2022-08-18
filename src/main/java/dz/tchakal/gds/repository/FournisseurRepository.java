package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {


    Optional<Fournisseur> findByMail(String mail);
}
