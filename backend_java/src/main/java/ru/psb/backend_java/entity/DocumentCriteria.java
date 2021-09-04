package ru.psb.backend_java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DocumentCriteria {

    @Id
    @GeneratedValue
    private Long id;

    private String criteriaName;

    private String criteriaValue;
}
