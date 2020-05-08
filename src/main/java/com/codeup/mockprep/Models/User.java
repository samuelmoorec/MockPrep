package com.codeup.mockprep.Models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private Long first_name;

    @Column
    private String last_name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<> images;

}
