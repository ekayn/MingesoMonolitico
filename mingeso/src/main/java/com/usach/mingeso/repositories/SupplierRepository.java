package com.usach.mingeso.repositories;

import com.usach.mingeso.entities.SupplierEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<SupplierEntity, Long> {

}
