package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class Pet implements Serializable {
    /**
     * Unique identifier for the pet.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Type of the pet.
     */
    private PetType type;

    /**
     * Name of the pet.
     */
    private String name;

    /**
     * Owner of the pet.
     */
    @ManyToOne(targetEntity = Customer.class, optional = false)
    private Customer customer;
    /**
     * Birth date of the pet.
     */
    private LocalDate birthDate;

    /**
     * Additional notes on the pet.
     */
    private String notes;
}