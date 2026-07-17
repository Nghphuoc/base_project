package com.application.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TB_ROLE")
@Data
public class Role extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roleName;
}
