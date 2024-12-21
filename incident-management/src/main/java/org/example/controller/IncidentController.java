package org.example.controller;

import jakarta.validation.Valid;
import org.example.entity.IncidentEntity;
import org.example.entity.ResEntity;
import org.example.service.IncidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentService service;

    @PostMapping("/incident")
    public Object createIncident(@Valid @RequestBody IncidentEntity incident){
        incident.setUpdateTime(LocalDateTime.now());
        incident.setCreateTime(LocalDateTime.now());
        service.addIncident(incident);
        return ResponseEntity.ok(new ResEntity("200","success"));
    }

    @PutMapping("/{id}")
    public Object updateIncident(@Valid @RequestBody IncidentEntity incident){
        incident.setUpdateTime(LocalDateTime.now());
        service.updateIncident(incident);
        return ResponseEntity.ok(new ResEntity("200","success"));
    }

    @DeleteMapping("/{id}")
    public Object deleteIncident(@PathVariable(value = "id") Long id){
        service.deleteIncident(id);
        return ResponseEntity.ok(new ResEntity("200","success"));
    }

    @GetMapping
    public Object queryPage(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size){

        Page<IncidentEntity> incidentEntities = service.queryPage(PageRequest.of(page, size, Sort.by("id").ascending()));

        List<IncidentEntity> content = incidentEntities.getContent();
        for (int i = 0; i < content.size(); i++) {
            IncidentEntity incidentEntity = content.get(i);
            IncidentEntity fromCache = service.getIncidentEntity(incidentEntity.getId());
            BeanUtils.copyProperties(fromCache,incidentEntity);
        }

        return new ResponseEntity(incidentEntities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Object getIncidentEntity(@PathVariable(value = "id") Long id){
        IncidentEntity incident = service.getIncidentEntity(id);
        return new ResponseEntity(incident, HttpStatus.OK);
    }
}
