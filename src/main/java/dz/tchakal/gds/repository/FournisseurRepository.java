package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Integer> {


    Optional<Fournisseur> findByMail(String mail);
}
