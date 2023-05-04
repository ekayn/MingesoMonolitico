package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "pays")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayEntity {
    @Id
    @Column(nullable=false, length = 5)
    private String code;
    private String category;
    private String date;
    private String name;
    private Integer milk;
    private Integer milkDays;
    private Double milkAverage;
    private Double milkChanged;
    private Integer grease;
    private Double greaseChanged;
    private Integer solid;
    private Double solidChanged;

    private Double milkPay;
    private Double milkTotalPay;
    private Double greasePay;
    private Double solidPay;

    private Double frecuencyBonification;
    private Double milkDiscount;
    private Double greaseDiscount;
    private Double solidDiscount;

    private Double pay;
    private Double totalDiscount;
    private Double retention;
    private Double totalPay;
}
