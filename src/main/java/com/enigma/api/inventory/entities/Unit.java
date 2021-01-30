package com.enigma.api.inventory.entities;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql =
        "UPDATE unit " +
                "SET is_deleted = true " +
                "WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Unit extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;
}
