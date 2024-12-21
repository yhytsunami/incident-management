package org.example.service;

import org.example.entity.IncidentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IncidentService {

    public Page<IncidentEntity> queryPage(Pageable pageable) ;
    public IncidentEntity addIncident(IncidentEntity incident);

    public IncidentEntity updateIncident(IncidentEntity incidentDetails);

    public void deleteIncident(Long id);

    IncidentEntity getIncidentEntity(Long id);
}
