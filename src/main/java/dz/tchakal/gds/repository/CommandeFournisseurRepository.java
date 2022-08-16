package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur,Integer> {


    Optional<CommandeFournisseur> findByCode(String code);
}
