package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * Customer service object used to perform database operations on customer entities.
     */
    @Autowired
    private CustomerService customerService;

    /**
     * Employee service object used to perform database operations on employee entities.
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * Converts a customer entity to a customer DTO.
     *
     * @param customer The customer entity to convert.
     * @return The customer DTO representing the customer entity.
     */
    private CustomerDTO getCustomerDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    /**
     * Gets all customers from the database.
     *
     * @return A list of customer DTOs representing all customers stored in the database.
     */
    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers()
                .stream()
                .map(this::getCustomerDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets the customer that owns the specified pet from the database.
     *
     * @param petId The ID of the pet whose owner to retrieve.
     * @return The customer DTO representing the owner of the specified pet.
     */
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return getCustomerDTO(customerService.getCustomerByPetId(petId));
    }

    /**
     * Converts an employee entity to an employee DTO.
     *
     * @param employee The employee entity to convert.
     * @return The employee DTO representing the employee entity.
     */
    private EmployeeDTO getEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        return employeeDTO;
    }

    /**
     * Retrieve an employee by their ID.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the employee with the given ID
     */
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return getEmployeeDTO(employeeService.getEmployeeById(employeeId));
    }

    /**
     * Set the availability of an employee.
     *
     * @param daysAvailable the set of days the employee is available
     * @param employeeId the ID of the employee to update
     */
    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    /**
     * Adds a new customer to the database.
     *
     * @param customerDTO The customer DTO representing the new customer.
     * @return The customer DTO representing the new customer as stored in the database.
     */
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setNotes(customerDTO.getNotes());
        List<Long> petIds = customerDTO.getPetIds();
        return getCustomerDTO(customerService.saveCustomer(customer, petIds));
    }

    /**
     * Adds a new employee to the database.
     *
     * @param employeeDTO The employee DTO representing the new employee.
     * @return The employee DTO representing the new employee as stored in the database.
     */
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        return getEmployeeDTO(employeeService.saveEmployee(employee));
    }

    /**
     * Find employees who are available for a given service.
     *
     * @param employeeDTO the EmployeeRequestDTO containing the date and required skills
     * @return a list of employees who are available for the requested service
     */
    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return employeeService.getEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills())
                .stream()
                .map(this::getEmployeeDTO)
                .collect(Collectors.toList());
    }
}