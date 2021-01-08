package com.sammidev.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String id;
    private String name;
    private String email;
    private Boolean active;
    private BigDecimal salary;

    private Date createdDate;
    private Timestamp createdTime;

    public Person(String name, String email, Boolean active, BigDecimal salary, Date createdDate, Timestamp createdTime) {
        this.name = name;
        this.email = email;
        this.active = active;
        this.salary = salary;
        this.createdDate = createdDate;
        this.createdTime = createdTime;
    }
}