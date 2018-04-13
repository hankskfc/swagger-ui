package com.chinac.doc.repository;


import com.chinac.doc.repository.entities.DocEntity;

import org.springframework.data.repository.CrudRepository;

public interface DocRepository extends CrudRepository<DocEntity, String> {
    DocEntity findByServiceNameAndServiceVersion(String serviceName, String serviceVersion);
}
