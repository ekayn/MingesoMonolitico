package com.usach.mingeso.repositories;

import com.usach.mingeso.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {
    @Query("select e.id from CollectionEntity e where e.id = :code")
    ArrayList<CollectionEntity> findAllCode(@Param("code") String code);
}
