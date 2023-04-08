package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "supplier")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long supplierId;
    @Column(unique=true,nullable=false, length = 5)
    private String supplierCode;
    @Column(unique=true,nullable = false, length = 45)
    private String supplierName;
    @Column(nullable = false, length = 1)
    private String supplierCategory;
    @Column(nullable = false, length = 3)
    private Boolean supplierRetention;

}
