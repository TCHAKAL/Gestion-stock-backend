package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Article;
import dz.tchakal.gds.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {


    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByEmailAndMotPasse(String email,String motPasse);
}
