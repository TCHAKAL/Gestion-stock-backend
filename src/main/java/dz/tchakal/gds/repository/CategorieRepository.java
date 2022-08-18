package dz.tchakal.gds.repository;

import dz.tchakal.gds.dto.CategorieDto;
import dz.tchakal.gds.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CategorieRepository extends JpaRepository<Categorie,Integer> {

    Categorie findByDesignation(String designation);

    Categorie findByCode(String code);
}
