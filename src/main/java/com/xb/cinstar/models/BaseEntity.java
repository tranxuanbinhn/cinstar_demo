package com.xb.cinstar.models;

import com.xb.cinstar.config.JpaAuditingConfig;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.event.EventListener;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.Date;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(updatable = false)
    @CreatedDate()
    private Date createDate;

    @Column(insertable = false)
    @LastModifiedDate
    private  Date modifiedDate;

    @Column(updatable = false)
    @CreatedBy
    private String createBy;

    @Column(insertable = false)
    @LastModifiedBy
    private String modifiedBy;

}
