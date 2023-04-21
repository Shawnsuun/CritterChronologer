package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customersRepository;

    @Autowired
    private PetRepository petsRepository;

    public CustomerService(CustomerRepository customersRepository, PetRepository petsRepository) {
        this.customersRepository = customersRepository;
        this.petsRepository = petsRepository;
    }

    /**
     * Retrieves all customers from the repository.
     *
     * @return a list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customersRepository.findAll();
    }

    /**
     * Retrieves the customer associated with the specified pet ID.
     *
     * @param petId the ID of the pet whose owner is to be retrieved
     * @return the customer associated with the specified pet ID
     */
    public Customer getCustomerByPetId(Long petId){
        return petsRepository.getOne(petId).getCustomer();
    }

    /**
     * Saves a new or updated customer entity to the repository.
     *
     * @param customer the customer entity to be saved
     * @param petIds a list of IDs of the pets to be associated with the customer
     * @return the saved customer entity
     */
    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> pets = new LinkedList<>();
        if (petIds != null && !petIds.isEmpty()) {
            pets = petsRepository.findAllById(petIds);
        }
        customer.setPets(pets);
        return customersRepository.save(customer);
    }
}
