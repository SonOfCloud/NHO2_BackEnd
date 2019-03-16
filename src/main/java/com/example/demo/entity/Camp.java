package com.example.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "camp")
public class Camp {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private String createdBy;

    @Column(insertable = false, updatable = false)
    private Date createdAt;

    private String updatedBy;

    @Column(insertable = false)
    private Date updatedAt;




}
