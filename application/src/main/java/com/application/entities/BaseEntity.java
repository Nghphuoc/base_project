package com.application.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * @author PhuocNH.
 *
 * BaseDaoSupport abstract class.
 */

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "CREATE_AT")
    protected Instant create_at;

    @Column(name = "UPDATE_AT")
    protected Instant update_at;

    @Column(name = "DELETE_FLAG")
    protected Boolean delete_flag;
}
