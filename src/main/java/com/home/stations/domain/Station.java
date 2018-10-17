package com.home.stations.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Indexed
@Getter @Setter @NoArgsConstructor
public class Station implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column(name = "VERSION")
    private long version;

    @Field
    private String stationId;

    @Field
    private String name;

    @Field
    private Boolean hdEnabled;

    private String callSign;
}
