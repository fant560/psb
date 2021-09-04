package ru.psb.backend_java.entity;

import lombok.Builder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
public class Document implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String documentNomenclatureId;

    private String inn;

    @Column(columnDefinition = "bool default false")
    private Boolean unrecognized;

    private String documentAbsPath;

    @OneToMany
    private List<DocumentCriteria> documentCriteriaList;
}
