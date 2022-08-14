package dz.tchakal.gds.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //pour assiger des valeur pour CreatedDate et LastModifiedDate
public class AbstractEntity implements Serializable {


    @CreatedDate
    @Column(name = "creation_date", nullable = false)
    @JsonIgnore
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    @JsonIgnore
    private Date lastModifiedDate;
}
