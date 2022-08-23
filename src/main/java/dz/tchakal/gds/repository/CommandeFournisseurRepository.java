package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.CommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur,Integer> {


    Optional<CommandeFournisseur> findByCode(String code);

    List<CommandeFournisseur> findAllByFournisseurId(Integer id);
}
