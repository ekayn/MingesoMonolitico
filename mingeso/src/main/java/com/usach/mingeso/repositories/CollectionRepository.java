package com.usach.mingeso.repositories;

import com.usach.mingeso.entities.CollectionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends CrudRepository<CollectionEntity, Long> {

}
