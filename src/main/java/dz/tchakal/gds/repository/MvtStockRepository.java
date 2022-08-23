package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.MvtStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MvtStockRepository extends JpaRepository<MvtStock, Integer> {

    @Query("select sum(m.quantite) from MvtStock m where m.article.id=:idArticle")
    BigDecimal stockReelArticle(@Param("idArticle") Integer idArticle);
    List<MvtStock> findAllByArticleId(Integer idArticle);

}
