package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {


    @Query("select u from Utilisateur u where u.email= :email")
    Utilisateur findByEmail(@Param("email") String email);

    Optional<Utilisateur> findByEmailAndMotPasse(String email,String motPasse);
}
