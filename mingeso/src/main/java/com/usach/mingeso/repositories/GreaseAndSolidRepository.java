package com.usach.mingeso.repositories;

import com.usach.mingeso.entities.GreaseAndSolidEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreaseAndSolidRepository extends CrudRepository<GreaseAndSolidEntity, Long> {

}
