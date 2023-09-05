package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class AuditableEntity {

    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateUpdated;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private CustomUser createdBy;
    @ManyToOne
    @JoinColumn(name = "last_updated_by")
    private CustomUser lastUpdatedBy;

}
