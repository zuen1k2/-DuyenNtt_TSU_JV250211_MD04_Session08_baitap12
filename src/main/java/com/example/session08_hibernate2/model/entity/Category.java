package com.example.session08_hibernate2.model.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cate_name", nullable = false, unique = true, length = 50)
    private String cateName;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "status")
    private Boolean status;

    //  1 Category có nhiều Book
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;
}

