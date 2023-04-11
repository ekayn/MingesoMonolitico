package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {
    @Id
    @NotNull
    private String supplierCode;
    private String supplierName;
    private String supplierCategory;
    private String supplierRetention;

}
