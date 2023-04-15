package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "collections")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionEntity {
    @Id
    @NotNull
    private String collectionSupplier;
    private String collectionDate;
    private String collectionShift;
    private String collectionMilk;
}
