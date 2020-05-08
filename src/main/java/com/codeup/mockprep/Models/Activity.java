package com.codeup.mockprep.Models;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private

    private String type;

    private Date date;
}
