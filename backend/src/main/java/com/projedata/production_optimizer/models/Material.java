package com.projedata.production_optimizer.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "materials")

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Material implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double stockQuantity;

}
