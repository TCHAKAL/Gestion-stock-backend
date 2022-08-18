package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {


    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByEmailAndMotPasse(String email,String motPasse);
}
