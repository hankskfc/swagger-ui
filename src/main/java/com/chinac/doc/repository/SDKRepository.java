package com.chinac.doc.repository;

import com.chinac.doc.repository.entities.SDKEntity;

import org.springframework.data.repository.CrudRepository;

public interface SDKRepository extends CrudRepository<SDKEntity, String> {

    SDKEntity findByServiceNameAndServiceVersion(String serviceName, String serviceVersion);
}
