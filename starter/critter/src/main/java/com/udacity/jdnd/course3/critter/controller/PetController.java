package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetsService petsService;

    /**
     * Converts Pet entity to PetDTO.
     *
     * @param pet The Pet entity to be converted.
     * @return The corresponding PetDTO object.
     */
    private PetDTO getPetsDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getType());
        return petDTO;
    }

    /**
     * Gets a pet by its ID.
     *
     * @param petId The ID of the pet to be retrieved.
     * @return The corresponding PetDTO object.
     */
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return getPetsDTO(petsService.getPetById(petId));
    }

    /**
     * Gets all pets in the database.
     *
     * @return A list of PetDTO objects.
     */
    @GetMapping
    public List<PetDTO> getPets(){
        return petsService.getAllPets()
                .stream()
                .map(this::getPetsDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets all pets of a particular customer.
     *
     * @param ownerId The ID of the customer whose pets are to be retrieved.
     * @return A list of PetDTO objects.
     */
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petsService.getPetsByCustomerId(ownerId)
                .stream()
                .map(this::getPetsDTO)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new pet in the database.
     *
     * @param petDTO The PetDTO object containing the details of the new pet.
     * @return The corresponding PetDTO object after being saved in the database.
     */
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        return getPetsDTO(petsService.savePet(pet, petDTO.getOwnerId()));
    }
}