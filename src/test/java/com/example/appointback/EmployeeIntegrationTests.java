package com.example.appointback;

import com.example.appointback.controller.EmployeeController;
import com.example.appointback.controller.TimeFrameController;
import com.example.appointback.entity.CalendarHolder;
import com.example.appointback.entity.EmployeeDto;
import com.example.appointback.entity.TimeFrameDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.appointback.entity.TimeFrame.TfStatus.Present;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@SpringBootTest
public class EmployeeIntegrationTests {

    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private TimeFrameController tfController;
    private Long empId;

    @BeforeEach
    public void prepareEmployees() {
        EmployeeDto employeeInput = new EmployeeDto(null, "abc", "Abc", CalendarHolder.Position.Manager, false);
        EmployeeDto employeeResponse = employeeController.createEmployee(employeeInput);
        empId = employeeResponse.getId();
    }

    @Test
    public void testCreateEmployee() {
        // given, when
        EmployeeDto emp = employeeController.getEmployees().stream()
                .findFirst().orElseThrow(IllegalArgumentException::new);
        // then
        assertEquals("abc", emp.getName());
    }

    @Test
    public void testGetSingleEmployee() {
        // given, when
        EmployeeDto emp = employeeController.getEmployee(empId);
        // then
        assertEquals("Abc", emp.getLastName());
    }

    @Test
    public void testUpdateEmployee() {
        // given
        TimeFrameDto tfInput = new TimeFrameDto(null, "2023-03-03", "08:00", "16:00", Present, empId);
        TimeFrameDto tfResponse = tfController.createTimeFrame(tfInput);
        List<Long> tfList = new ArrayList<>(Collections.singletonList(tfResponse.getId()));
        EmployeeDto empUpdated = new EmployeeDto(empId,"abc","ABC", CalendarHolder.Position.Board, false,null,tfList);
        // when
        EmployeeDto updatedEmployeeResponse = employeeController.updateEmployee(empUpdated);
        // then
        assertEquals("ABC", updatedEmployeeResponse.getLastName());
    }

    @Test
    public void testDeleteEmployee () {
        // given
        int initialSize = employeeController.getEmployees().size();
        // when
        employeeController.deleteEmployee(empId);
        int finalSize = employeeController.getEmployees().size();
        // then
        assertTrue(initialSize > finalSize);
    }
}
