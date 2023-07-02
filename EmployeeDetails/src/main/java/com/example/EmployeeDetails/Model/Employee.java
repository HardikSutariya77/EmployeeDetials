package com.example.EmployeeDetails.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee {

    @Id
    private int id;

    private String name;


    @Column(unique = true)
    private String emailId;
    private String department;
    private int salary;

    @CreationTimestamp
    private Date joiningDate;

    @UpdateTimestamp
    @JsonIgnore
    private Date lastUpdatedDate;
}
