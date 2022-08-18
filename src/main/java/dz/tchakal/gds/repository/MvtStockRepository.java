package dz.tchakal.gds.repository;

import dz.tchakal.gds.model.MvtStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MvtStockRepository extends JpaRepository<MvtStock,Integer> {

}
