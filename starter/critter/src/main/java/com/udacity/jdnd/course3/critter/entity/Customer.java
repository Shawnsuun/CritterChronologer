package com.udacity.jdnd.course3.critter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a customer in the system.
 */
@Data
@Entity
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String phoneNumber;
    private String notes;
    private String name;

    @OneToMany(targetEntity = Pet.class)
    private List<Pet> pets;

    /**
     * Adds a pet belonging to this customer.
     * @param pet the pet to insert
     */
    public void addPet(Pet pet) {
        pets.add(pet);
    }
}