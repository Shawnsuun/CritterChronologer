package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Schedule implements Serializable {
    /**
     * Unique ID of the schedule
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * List of employees scheduled for the appointment
     */
    @ManyToMany(targetEntity = Employee.class)
    private  List<Employee> employees;

    /**
     * List of pets scheduled for the appointment
     */
    @ManyToMany(targetEntity = Pet.class)
    private List<Pet> pets;

    /**
     * Date of the appointment
     */
    private LocalDate date;

    /**
     * Set of activities for the appointment
     */
    @ElementCollection
    private Set<EmployeeSkill> activities;
}