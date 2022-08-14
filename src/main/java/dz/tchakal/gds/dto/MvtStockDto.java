package dz.tchakal.gds.dto;

import dz.tchakal.gds.model.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
public class MvtStockDto{

    BigDecimal quantite;
}
