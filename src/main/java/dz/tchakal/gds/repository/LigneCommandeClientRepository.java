package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient,Integer> {


    List<LigneCommandeClient> findAllByCommandeClientId(Integer idCommande);
}
