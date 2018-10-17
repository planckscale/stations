package com.home.stations.stations.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Indexed
@Getter @Setter @NoArgsConstructor
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Field
    private String stationId;
    @Field
    private String name;
    @Field
    private Boolean hdEnabled;
    @Field
    private String callSign;
}
