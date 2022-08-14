package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "vente")
@Entity
public class Vente extends AbstractEntity{

    @Column(name = "code")
    private  String code;
}
