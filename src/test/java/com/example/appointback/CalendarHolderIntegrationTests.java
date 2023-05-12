package com.example.appointback;

import com.example.appointback.controller.DoctorController;
import com.example.appointback.controller.SchedulerController;
import com.example.appointback.entity.DoctorDto;
import com.example.appointback.entity.SchedulerDto;
import com.example.appointback.entityfactory.CalendarHolderController;
import com.example.appointback.entityfactory.FactoryDtoOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CalendarHolderIntegrationTests {

    @Autowired
    private DoctorController doctorController;
    @Autowired
    private SchedulerController schedulerController;
    @Autowired
    private CalendarHolderController calendarHolderController;

    @Test
    public void testChGet() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "abc", "xyz", "Board", false));
        SchedulerDto scheduler = schedulerController.createScheduler(new SchedulerDto(null, "scheduler"));
        // when
        DoctorDto doc = (DoctorDto) calendarHolderController.getCH(doctor.getId());
        SchedulerDto sc = (SchedulerDto) calendarHolderController.getCH(scheduler.getId());
        // then
        assertAll(() -> assertEquals("Board", doc.getPosition()), () -> assertEquals("scheduler", sc.getName()));
    }

    @Test
    public void testChGetAll() {
        // given
        doctorController.createDoctor(new DoctorDto(null, "abc", "xyz", "Board", false));
        schedulerController.createScheduler(new SchedulerDto(null, "scheduler"));
        // when
        List<FactoryDtoOutput> list = calendarHolderController.getAllCH();
        // then
        assertEquals(2, list.size());
    }

    @Test
    public void testChDelete() {
        // given
        DoctorDto doctor = doctorController.createDoctor(new DoctorDto(null, "abc", "xyz", "Board", false));
        SchedulerDto scheduler = schedulerController.createScheduler(new SchedulerDto(null, "scheduler"));
        // when
        int before = calendarHolderController.getAllCH().size();
        calendarHolderController.deleteCH(doctor.getId());
        int after = calendarHolderController.getAllCH().size();
        // then
        assertTrue(before > after);
    }
}
