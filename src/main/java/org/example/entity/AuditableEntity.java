package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.OneToOne;
import java.util.Date;

@Setter
@Getter
public abstract class AuditableEntity {

    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateUpdated;
    @OneToOne(mappedBy = "userId")
    private CustomUser CreatedBy;
    @OneToOne(mappedBy = "userId")
    private CustomUser UpdateBy;

}
