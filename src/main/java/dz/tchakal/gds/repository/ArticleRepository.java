package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    Article findByCode(String code);

    List<Article> findByCodeIgnoreCaseAndDesignationIgnoreCase(String code,String designation);

    //Exemple de Recherche par code et designation par une requete JPQL
    @Query("select a from  Article a where a.code=:code and a.designation=:designation")
    List<Article> findByCustomQuery(String code,@Param("designation") String d);

    //Exemple de Recherche par code et designation par une requete JPQL native
    @Query(value = "select a from  Article a where a.code=:code",nativeQuery = true)
    List<Article> findByCustomQuery(String code);



}
