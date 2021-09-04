package ru.psb.backend_java.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Document implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String documentNomenclatureId;

    @Column(columnDefinition = "bool default false")
    private Boolean unrecognized;

    private String documentAbsPath;

    @OneToMany
    private List<DocumentCriteria> documentCriteriaList;
}
