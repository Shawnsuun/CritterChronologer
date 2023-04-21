package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Set;

/**
 * This class represents an employee entity with its associated properties and methods
 */

@Data
@Entity
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    /**
     * The employee's set of skills
     */
    @ElementCollection
    private Set<EmployeeSkill> skills;

    /**
     * The employee's set of days available for work
     */
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;
}