package ua.teamchallenge.survivalstore.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private String image;

    @Column(name = "is_top")
    private Boolean isTop;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentCategory;
}

