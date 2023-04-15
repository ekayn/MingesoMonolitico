package com.usach.mingeso.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "greaseAndSolids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreaseAndSolidEntity {
    @Id
    @NotNull
    private String greaseAndSolidCode;
    private String greaseAndSolidGrease;
    private String greaseAndSolidSolid;
}
