package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@Transactional
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private SchedulesService schedulesService;

    /**
     * Converts a Schedule entity to a ScheduleDTO.
     *
     * @param schedule the Schedule entity to convert
     * @return the ScheduleDTO created from the Schedule entity
     */
    private ScheduleDTO getScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }

    /**
     * Creates a new Schedule.
     *
     * @param scheduleDTO the ScheduleDTO containing the new schedule information
     * @return the created ScheduleDTO
     */
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        return getScheduleDTO(schedulesService.saveSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
    }

    /**
     * Retrieves all schedules.
     *
     * @return a List of all ScheduleDTOs
     */
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return schedulesService.getAllSchedules()
                .stream()
                .map(this::getScheduleDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all schedules associated with a specific pet.
     *
     * @param petId the ID of the pet
     * @return a List of all ScheduleDTOs associated with the pet
     */
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return schedulesService.getAllSchedulesForPet(petId)
                .stream()
                .map(this::getScheduleDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all schedules associated with a specific employee.
     *
     * @param employeeId the ID of the employee
     * @return a List of all ScheduleDTOs associated with the employee
     */
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return schedulesService.getAllSchedulesForEmployee(employeeId)
                .stream()
                .map(this::getScheduleDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all schedules associated with a specific customer.
     *
     * @param customerId the ID of the customer
     * @return a List of all ScheduleDTOs associated with the customer's pets
     */
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return schedulesService.getAllScheduleForCustomer(customerId)
                .stream()
                .map(this::getScheduleDTO)
                .collect(Collectors.toList());
    }
}