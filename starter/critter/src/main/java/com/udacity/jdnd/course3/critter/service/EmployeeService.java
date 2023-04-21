package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    /**
     * An instance of EmployeeRepository that is used to interact with the employee database table
     */
    @Autowired
    private final EmployeeRepository employeesRepository;

    /**
     * Creates an instance of EmployeeService
     * @param employeesRepository The EmployeeRepository used to interact with the employee table
     */
    public EmployeeService(EmployeeRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    /**
     * Retrieves an employee from the database by its ID
     * @param employeeId The ID of the employee to retrieve
     * @return The employee with the specified ID
     */
    public Employee getEmployeeById(Long employeeId) {
        return employeesRepository.getOne(employeeId);
    }

    /**
     * Retrieves a list of employees that are available to perform the specified service on the specified date
     * @param date The date on which the service is required
     * @param skills The skills required for the service
     * @return A list of employees available for the service
     */
    public List<Employee> getEmployeesForService(LocalDate date, Set<EmployeeSkill> skills){
        return employeesRepository.getAllByDaysAvailableContains(date.getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    /**
     * Saves an employee to the database
     * @param employee The employee to save
     * @return The saved employee
     */
    public Employee saveEmployee(Employee employee) {
        return employeesRepository.save(employee);
    }

    /**
     * Sets the availability of an employee
     * @param days The days the employee is available
     * @param employeeId The ID of the employee whose availability is being set
     */
    public void setEmployeeAvailability(Set<DayOfWeek> days, Long employeeId) {
        Employee employee = employeesRepository.getOne(employeeId);
        employee.setDaysAvailable(days);
        employeesRepository.save(employee);
    }
}