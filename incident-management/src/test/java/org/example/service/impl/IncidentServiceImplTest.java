package org.example.service.impl;

import org.example.config.CacheConfig;
import org.example.entity.IncidentEntity;
import org.example.repository.IncidentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
public class IncidentServiceImplTest {
    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(cacheManager.getCache(CacheConfig.INCIDENT_ENTITY)).thenReturn(cache);
    }

    @Test
    public void testCreateIncident() {
        IncidentEntity IncidentEntity = new IncidentEntity(null, "title", "description", LocalDateTime.now(), LocalDateTime.now(), "END");
        IncidentEntity savedIncident = new IncidentEntity(1L, "title", "description", LocalDateTime.now(), LocalDateTime.now(), "END");
        when(incidentRepository.save(IncidentEntity)).thenReturn(savedIncident);

        IncidentEntity result = incidentService.addIncident(IncidentEntity);
        assertEquals(savedIncident, result);
        verify(incidentRepository).save(IncidentEntity);
    }

    @Test
    public void testUpdateIncident() {
        Long id = 1L;
        IncidentEntity incidentDetails = new IncidentEntity(id, "title1", "description1",  LocalDateTime.now(), LocalDateTime.now(), "END");
        IncidentEntity existingIncident = new IncidentEntity(id, "title", "description",  LocalDateTime.now(), LocalDateTime.now(), "DEALING");

        when(incidentRepository.findById(id)).thenReturn(Optional.of(existingIncident));
        when(incidentRepository.save(incidentDetails)).thenReturn(incidentDetails);
        IncidentEntity updatedIncident = incidentService.updateIncident(incidentDetails);

        assertNotNull(updatedIncident);
        assertEquals("title1", updatedIncident.getName());
        assertEquals("description1", updatedIncident.getDescription());
        assertEquals("END", updatedIncident.getStatus());
        verify(incidentRepository).save(updatedIncident);
    }
    
    @Test
    public void testGetPage() {
        Pageable pageable = PageRequest.of(0, 1);
        IncidentEntity IncidentEntity = new IncidentEntity(1L, "title", "description", LocalDateTime.now(), LocalDateTime.now(), "END");
        Page<IncidentEntity> page = new PageImpl<>(Collections.singletonList(IncidentEntity));
        when(incidentRepository.findAll(pageable)).thenReturn(page);

        Page<IncidentEntity> result = incidentService.queryPage( pageable);
        assertEquals(1, result.getTotalElements());
        verify(incidentRepository).findAll(pageable);
    }

    @Test
    public void testDeleteIncident() {
        Long id = 1L;
        when(incidentRepository.existsById(id)).thenReturn(true);

        incidentService.deleteIncident(id);

        verify(incidentRepository).deleteById(id);

    }
}
