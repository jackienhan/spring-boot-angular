package com.bezkoder.model;



import lombok.*;
import javax.persistence.*;


@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 20)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
