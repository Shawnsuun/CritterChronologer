package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetsService {
    @Autowired
    private final PetRepository petsRepository;

    @Autowired
    private final CustomerRepository customersRepository;

    /**
     * Constructor with dependencies.
     * @param petsRepository repository for pet entities
     * @param customersRepository repository for customer entities
     */
    public PetsService(PetRepository petsRepository, CustomerRepository customersRepository) {
        this.petsRepository = petsRepository;
        this.customersRepository = customersRepository;
    }

    /**
     * Gets a list of all pets.
     * @return list of pet entities
     */
    public List<Pet> getAllPets() {
        return petsRepository.findAll();
    }

    /**
     * Gets a list of all pets belonging to a specific customer.
     * @param customerId id of the customer entity
     * @return list of pet entities
     */
    public List<Pet> getPetsByCustomerId(Long customerId){
        return petsRepository.getAllByCustomerId(customerId);
    }

    /**
     * Gets a pet by its id.
     * @param petId id of the pet entity
     * @return pet entity
     */
    public Pet getPetById(Long petId){
        return petsRepository.getOne(petId);
    }

    /**
     * Saves a new pet entity and links it to a customer entity.
     * @param pet new pet entity to save
     * @param ownerId id of the owner customer entity
     * @return saved pet entity
     * @throws EntityNotFoundException if the owner customer entity does not exist
     */
    public Pet savePet(Pet pet, Long ownerId) {
        Customer customer = customersRepository.findById(ownerId).orElseThrow(EntityNotFoundException::new);
        pet.setCustomer(customer);
        pet = petsRepository.save(pet);
        customer.addPet(pet);
        customersRepository.save(customer);
        return pet;
    }
}