package org.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.example.entity.IncidentEntity;
import org.example.entity.ResEntity;
import org.example.service.IncidentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class IncidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncidentService incidentService;

    @Autowired
    private ObjectMapper objectMapper;

    private IncidentEntity mockEntity;

    @BeforeEach
    public void setUp() {
        mockEntity = new IncidentEntity();
        mockEntity.setId(1L);
        mockEntity.setName("title");
        mockEntity.setDescription("description");
        mockEntity.setStatus("END");
        mockEntity.setCreateTime(LocalDateTime.now());
    }

    private Page<IncidentEntity> buildPageResult() {
        return new PageImpl<>(List.of(mockEntity), PageRequest.of(0, 1), 1);
    }

    private String getJsonContent(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    @Test
    public void testCreateIncident() throws Exception {
        // Setup
        when(incidentService.addIncident(any(IncidentEntity.class))).thenReturn(mockEntity);

        // Run the test
        String jsonContent = getJsonContent(mockEntity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/incidents/incident")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the results
        String expectedJson = getJsonContent(new ResEntity("200","success"));
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void testGetAllIncidents() throws Exception {
        // Setup
        when(incidentService.queryPage( any(PageRequest.class)))
                .thenReturn(buildPageResult());
        when(incidentService.getIncidentEntity( anyLong()))
                .thenReturn(mockEntity);


        // Run the test
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/incidents")
                        .param("title", "title")
                        .param("page", "0")
                        .param("size", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the results
        String expectedJson = getJsonContent(new ResponseEntity(buildPageResult(), HttpStatus.OK).getBody());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    public void testUpdateIncident() throws Exception {
        // Setup
        when(incidentService.updateIncident(any(IncidentEntity.class))).thenReturn(mockEntity);

        // Run the test
        String jsonContent = getJsonContent(mockEntity);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/incidents/1")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the results
        String expectedJson = getJsonContent(new ResEntity("200","success"));
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
    }


    @Test
    public void testDeleteIncident() throws Exception {
        // Run the test
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/incidents/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Verify the results
        String expectedJson = getJsonContent(new ResEntity("200","success"));
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(expectedJson);
    }

}
