package com.user.service.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "user_sequence", sequenceName = "user_seq", allocationSize = 1)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE ,generator = "user_sequence")
    private long id;

    private String name;

    private String email;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Transient
    private List<Todo> todo = new ArrayList<>();

}
