package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneVenteRepository extends JpaRepository<LigneVente,Integer> {


}
