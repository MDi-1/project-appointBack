package com.example.appointback.controller;

import com.example.appointback.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeMapper {
    
    private AppointmentRepository appointmentRepository;
    private TimeFrameRepository timeFrameRepository;

    public Employee mapToEmployee(EmployeeDto dto) {
        List<Appointment> appointments;
        List<TimeFrame> timeFrames;
        if (dto.getAppointmentIds() != null) {
            appointments = dto.getAppointmentIds().stream().map(aLong -> appointmentRepository.findById(aLong)
                    .orElseThrow(IllegalArgumentException::new)).collect(Collectors.toList());
        } else { appointments = null; }
        if (dto.getTimeFrameIds() != null) {
            timeFrames = dto.getTimeFrameIds().stream().map(aLong -> timeFrameRepository.findById(aLong)
                    .orElseThrow(IllegalArgumentException::new)).collect(Collectors.toList());
        } else { timeFrames = null; }
        return new Employee(dto.getId(), dto.getName(), dto.getLastName(), dto.getPosition(), dto.isGoCalendarSync(),
                appointments, timeFrames);
    }

    public EmployeeDto mapToNewEmployeeDto(final Employee emp) {
        return new EmployeeDto(emp.getId(),emp.getName(),emp.getLastName(),emp.getPosition(),emp.isGoCalendarSync());
    }

    public EmployeeDto mapToEmployeeDto(final Employee employee) {
        List<Long> listOfTimeFrames;
        List<Long> listOfAppIds;
        if (employee.getTimeFrames() != null) {
            listOfTimeFrames = employee.getTimeFrames().stream().map(TimeFrame::getId).collect(Collectors.toList());
        } else listOfTimeFrames = new ArrayList<>();
        if (employee.getAppointments() != null) {
            listOfAppIds = employee.getAppointments().stream().map(Appointment::getId).collect(Collectors.toList());
        } else listOfAppIds = new ArrayList<>();
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getLastName(), employee.getPosition(),
                employee.isGoCalendarSync(), listOfTimeFrames, listOfAppIds);
    }

    public List<EmployeeDto> mapToEmployeeDtoList(final List<Employee> employeeList) {
        return employeeList.stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
    }
}
