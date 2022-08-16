package dz.tchakal.gds.repository;

import dz.tchakal.gds.dto.CommandeClientDto;
import dz.tchakal.gds.model.CommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommandeClientRepository extends JpaRepository<CommandeClient,Integer> {


    Optional<CommandeClient> findByCode(String code);
}
