package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface VenteRepository extends JpaRepository<Vente,Integer> {


    Optional<Vente> findByCode(String code);

    Optional<Vente> findByDateVente(Instant dateVente);
}
