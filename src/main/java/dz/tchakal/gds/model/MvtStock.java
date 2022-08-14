package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "mvt_stock")
@Entity
public class MvtStock extends AbstractEntity{

    BigDecimal quantite;
}
