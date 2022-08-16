package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {

    Optional<Client> findByMail(String mail);
}
