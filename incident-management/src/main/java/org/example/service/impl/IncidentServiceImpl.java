package org.example.service.impl;

import org.example.config.BizException;
import org.example.config.CacheConfig;
import org.example.entity.IncidentEntity;
import org.example.repository.IncidentRepository;
import org.example.service.IncidentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.config.BizException.INC_10001;

@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentRepository repository;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Page<IncidentEntity> queryPage( Pageable pageable) {
        Page<IncidentEntity> incidentEntities = repository.findAll( pageable);
        return incidentEntities;
    }

    @Override
    @CachePut(value = CacheConfig.INCIDENT_ENTITY, key = "#result.id")
    public IncidentEntity addIncident(IncidentEntity incident) {
        IncidentEntity save = repository.save(incident);
        return save;
    }

    @Override
    @CachePut(value = CacheConfig.INCIDENT_ENTITY, key = "#result.id")
    public IncidentEntity updateIncident(IncidentEntity incidentDetails) {
        IncidentEntity save = repository.save(incidentDetails);
        cacheManager.getCache(CacheConfig.INCIDENT_ENTITY).evict(incidentDetails.getId());
        return save;
    }

    @Override
    @Caching(evict = {@CacheEvict(value = CacheConfig.INCIDENT_ENTITY, key = "#id")})
    public void deleteIncident(Long id) {
        if (!repository.existsById(id)) {
            throw new BizException(INC_10001,"Incident not exists, id:"+id);
        }
        repository.deleteById(id);
    }

    @Override
    @Cacheable(value = CacheConfig.INCIDENT_ENTITY, key = "#id")
    public IncidentEntity getIncidentEntity(Long id) {
        return repository.getReferenceById(id);
    }


}
