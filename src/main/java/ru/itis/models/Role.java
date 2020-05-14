package ru.itis.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@Entity
@Data
@Table(schema = "finproj", name = "roles")
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToMany(mappedBy = "roles")
    @Transient
    private Set<User> user;
    private String name;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
