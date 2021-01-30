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
        "UPDATE stock " +
                "SET is_deleted = true " +
                "WHERE id = ?")
@Where(clause = "is_deleted = false")
public class Stock extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private Integer quantity;
}
