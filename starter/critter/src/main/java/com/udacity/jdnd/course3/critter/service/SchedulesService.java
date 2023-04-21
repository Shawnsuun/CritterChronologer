package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SchedulesService {
    @Autowired
    private final ScheduleRepository scheduleRepository;

    @Autowired
    private final PetRepository petsRepository;

    @Autowired
    private final EmployeeRepository employeesRepository;

    @Autowired
    private final CustomerRepository customersRepository;

    /**
     * Constructor for creating a new instance of {@link SchedulesService}
     * @param scheduleRepository The schedule repository instance
     * @param petsRepository The pet repository instance
     * @param employeesRepository The employee repository instance
     * @param customersRepository The customer repository instance
     */
    public SchedulesService(ScheduleRepository scheduleRepository, PetRepository petsRepository, EmployeeRepository employeesRepository, CustomerRepository customersRepository) {
        this.scheduleRepository = scheduleRepository;
        this.petsRepository = petsRepository;
        this.employeesRepository = employeesRepository;
        this.customersRepository = customersRepository;
    }

    /**
     * Get all schedules
     * @return A list of all schedules
     */
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    /**
     * Get all schedules for a pet
     * @param petId The id of the pet
     * @return A list of schedules associated with the pet
     */
    public List<Schedule> getAllSchedulesForPet(Long petId) {
        return scheduleRepository.getAllByPetsContains(petsRepository.getOne(petId));
    }


    /**
     * Get all schedules for an employee
     * @param employeeId The id of the employee
     * @return A list of schedules associated with the employee
     */
    public List<Schedule> getAllSchedulesForEmployee(Long employeeId) {
        return scheduleRepository.getAllByEmployeesContains(employeesRepository.getOne(employeeId));
    }


    /**
     * Get all schedules for a customer
     * @param customerId The id of the customer
     * @return A list of schedules associated with the customer's pets
     */
    public List<Schedule> getAllScheduleForCustomer(Long customerId) {
        return scheduleRepository.getAllByPetsIn(customersRepository.getOne(customerId).getPets());
    }

    /**
     * Save a schedule
     * @param schedule The schedule to be saved
     * @param employeeIds A list of employee ids associated with the schedule
     * @param petIds A list of pet ids associated with the schedule
     * @return The saved schedule
     */
    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeIds.stream()
                .map(employeesRepository::getOne)
                .collect(Collectors.toList());

        List<Pet> pets = petIds.stream()
                .map(petsRepository::getOne)
                .collect(Collectors.toList());

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }
}