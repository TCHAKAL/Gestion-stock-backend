package dz.tchakal.gds.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true )
@Table(name = "role")
@Entity
public class Role extends AbstractEntity{

    private String nom;
}
