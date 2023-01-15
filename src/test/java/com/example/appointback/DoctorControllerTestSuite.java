package com.example.appointback;

import com.example.appointback.controller.DoctorController;
import com.example.appointback.entity.DoctorDto;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(DoctorController.class)
public class DoctorControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorController controller;

    @Test
    public void testCreateDoctor() throws Exception {
        // given
        DoctorDto dtoIn  = new DoctorDto(null, "AbcName", "AbcLastname", "Board");
        DoctorDto dtoOut = new DoctorDto(1L, "AbcName", "AbcLastname", "Board");
        Gson gson = new Gson();
        String json = gson.toJson(dtoIn);
        // when
        when(controller.createDoctor(any(DoctorDto.class))).thenReturn(dtoOut);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/doctor")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(json))
        //then
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("AbcName")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("AbcLastname")))
            .andExpect(MockMvcResultMatchers.jsonPath("$.position", Matchers.is("Board")));
    }

    @Test
    public void testUpdateDoctor() throws Exception {
        // given
        DoctorDto dtoIn  = new DoctorDto(null, "AbcName", "AbcLastname", "Board");
        DoctorDto dtoOut = new DoctorDto(1L, "AbcName", "AbcLastname", "Board");
        Gson gson = new Gson();
        String json = gson.toJson(dtoIn);
        // when
        when(controller.createDoctor(any(DoctorDto.class))).thenReturn(dtoOut);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8").content(json))
                //then
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("AbcName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("AbcLastname")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position", Matchers.is("Board")));
    }
}
