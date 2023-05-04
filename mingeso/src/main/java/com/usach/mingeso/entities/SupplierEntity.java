package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierEntity {
    @Id
    @Column(nullable=false, length = 5)
    private String code;
    private String name;
    private String category;
    private String retention;
}
