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
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) //pour assiger des valeur pour CreatedDate et LastModifiedDate
public class AbstractEntity implements Serializable {


    @CreatedDate
    @Column(name = "creation_date",nullable = false,updatable = false)
    private Instant creationDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

//    @PrePersist
//    public void prePersist(){
//        creationDate = Instant.now();
//    }
//    @PreUpdate
//    public void PreUpdate(){
//        lastModifiedDate = Instant.now();
//    }
}
