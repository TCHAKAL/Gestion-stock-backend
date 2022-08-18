package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository

public interface CommandeClientRepository extends JpaRepository<CommandeClient,Integer> {


    Optional<CommandeClient> findByCode(String code);
}
