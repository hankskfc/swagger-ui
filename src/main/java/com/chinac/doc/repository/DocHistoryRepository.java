package com.chinac.doc.repository;

import com.chinac.doc.repository.entities.DocHistoryEntity;

import org.springframework.data.repository.CrudRepository;

public interface DocHistoryRepository extends CrudRepository<DocHistoryEntity, String> {

}
