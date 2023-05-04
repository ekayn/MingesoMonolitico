package com.usach.mingeso.services;

import com.usach.mingeso.entities.*;
import com.usach.mingeso.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PayService {
    @Autowired
    PayRepository payRepository;

    @Autowired
    RegisterService registerService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    @Autowired
    SupplierService supplierService;

    public void pagarPorId(String code){
        ArrayList<CollectionEntity> acopios = collectionService.obtenerAcopiosCodigo(code);
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        GreaseAndSolidEntity grasaSolido = greaseAndSolidService.obtenerGrasasSolidosCodigo(code);
        RegisterEntity registro = registerService.obtenerRegistroCodigo(code);

        PayEntity pago = new PayEntity();
        pago.setDate("2020/1/1");
        pago.setCode(code);
        pago.setCategory(proveedor.getCategory());
        pago.setName(proveedor.getName());
        int totalLeche = 0;
        int totalDias = 0;
        int totalTarde = 0;
        int totalManana = 0;

        for (CollectionEntity acopio : acopios){
            totalLeche = totalLeche + acopio.getMilk();
            totalDias = totalDias+1;
            if (acopio.getShift().equals("M")){
                totalManana = totalManana+1;
            } else if (acopio.getShift().equals("T")){
                totalTarde = totalTarde+1;
            }
        }
        pago.setMilkAverage((double) totalLeche/totalDias);
        pago.setMilkDays(totalDias);

        pago.setMilkChanged((double) 100*(Math.abs(1 - totalLeche/registro.getMilk())));

        pago.setGrease(grasaSolido.getGrease());
        pago.setGreaseChanged((double) 100*(Math.abs(1 - grasaSolido.getGrease()/registro.getGrease())));
        pago.setSolid(grasaSolido.getSolid());
        pago.setSolidChanged((double) 100*(Math.abs(1 - grasaSolido.getSolid())/registro.getSolid()));

        switch (proveedor.getCategory()) {
            case "A" -> pago.setMilkPay((double) totalLeche * 700);
            case "B" -> pago.setMilkPay((double) totalLeche * 550);
            case "C" -> pago.setMilkPay((double) totalLeche * 400);
            case "D" -> pago.setMilkPay((double) totalLeche * 250);
        }

        if (grasaSolido.getGrease() <= 20){
            pago.setGreasePay((double) totalLeche * 30);
        } else if (grasaSolido.getGrease() <= 45){
            pago.setGreasePay((double) totalLeche * 80);
        } else{
            pago.setGreasePay((double) totalLeche * 120);
        }

        if (grasaSolido.getSolid() <= 7){
            pago.setSolidPay((double) totalLeche * -130);
        } else if (grasaSolido.getSolid() <= 18){
            pago.setSolidPay((double) totalLeche * -90);
        } else if (grasaSolido.getSolid() <= 35){
            pago.setSolidPay((double) totalLeche * 95);
        } else{
            pago.setSolidPay((double) (totalLeche * 150));
        }

        if (totalManana > 10 && totalTarde > 10){
            pago.setFrecuencyBonification(pago.getMilkPay() * 0.20);
        } else if (totalManana > 10){
            pago.setFrecuencyBonification(pago.getMilkPay() * 0.12);
        } else if (totalTarde > 10){
            pago.setFrecuencyBonification(pago.getMilkPay() * 0.8);
        } else{
            pago.setFrecuencyBonification(0.0);
        }

        if (pago.getMilkChanged() >= 100){
            pago.setMilkDiscount(0.0);
        } else if (pago.getMilkChanged() >= 46){
            pago.setMilkDiscount(pago.getMilkPay() * 0.30);
        } else if (pago.getMilkChanged() >= 26) {
            pago.setMilkDiscount(pago.getMilkPay() * 0.15);
        } else if (pago.getMilkChanged() >= 9){
            pago.setMilkDiscount(pago.getMilkPay() * 0.7);
        } else{
            pago.setMilkDiscount(0.0);
        }

        if (pago.getGreaseChanged() >= 100){
            pago.setGreaseDiscount(0.0);
        } else if (pago.getGreaseChanged() >= 41){
            pago.setGreaseDiscount(pago.getMilkPay() * 0.30);
        } else if (pago.getGreaseChanged() >= 26) {
            pago.setGreaseDiscount(pago.getMilkPay() * 0.20);
        } else if (pago.getGreaseChanged() >= 16){
            pago.setGreaseDiscount(pago.getMilkPay() * 0.12);
        } else{
            pago.setGreaseDiscount(0.0);
        }

        if (pago.getSolidChanged() >= 100){
            pago.setSolidDiscount(0.0);
        } else if (pago.getSolidChanged() >= 36){
            pago.setSolidDiscount(pago.getMilkPay() * 0.45);
        } else if (pago.getSolidChanged() >= 13) {
            pago.setSolidDiscount(pago.getMilkPay() * 0.27);
        } else if (pago.getSolidChanged() >= 7){
            pago.setSolidDiscount(pago.getMilkPay() * 0.18);
        } else{
            pago.setSolidDiscount(0.0);
        }

        pago.setMilkTotalPay(pago.getMilkPay() + pago.getGreasePay() + pago.getSolidPay() + pago.getFrecuencyBonification());
        pago.setTotalDiscount(pago.getMilkDiscount() + pago.getGreaseDiscount() + pago.getSolidDiscount());
        pago.setPay(pago.getMilkTotalPay() - pago.getTotalDiscount());

        if (proveedor.getRetention().equals("Si")){
            if (pago.getPay() > 950000){
                pago.setTotalPay(pago.getPay() - pago.getPay()*0.13);
            } else if (pago.getPay() < 0.0) {
                pago.setTotalPay(0.0);
            } else{
                pago.setTotalPay(pago.getPay());
            }
        }else if (proveedor.getRetention().equals("No")){
            if (pago.getPay() < 0.0) {
                pago.setTotalPay(0.0);
            } else{
                pago.setTotalPay(pago.getPay());
            }
        } else {
            pago.setTotalPay(0.0);
        }
    }
}
