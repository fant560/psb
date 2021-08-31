package ru.psb.backend_java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Period implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
