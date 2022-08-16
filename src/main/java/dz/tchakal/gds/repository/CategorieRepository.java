package dz.tchakal.gds.repository;

import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie,Integer> {

    Categorie findByDesignation(String designation);
}
