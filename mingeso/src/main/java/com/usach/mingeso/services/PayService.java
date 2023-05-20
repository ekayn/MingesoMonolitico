package com.usach.mingeso.services;

import com.usach.mingeso.entities.*;
import com.usach.mingeso.repositories.*;
import lombok.Generated;
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

    public ArrayList<PayEntity> obtenerPagos(){
        return (ArrayList<PayEntity>) payRepository.findAll();
    }

    public Double pagoTotalLeche(Double milkPay, Double greasePay, Double solidPay, Double frecuencyBonification){
        double milk = 1.0;
        double grease = 1.0;
        double solid = 1.0;
        double frecuency = 1.0;
        if (milkPay < 0.0){
            milk = 0.0;
        }
        if (greasePay < 0.0){
            grease = 0.0;
        }
        if (solidPay < 0.0){
            solid = 0.0;
        }
        if (frecuencyBonification < 0.0){
            frecuency = 0.0;
        }
        return milk * milkPay + grease * greasePay + solid * solidPay + frecuency * frecuencyBonification;
    }

    public Double descuentoTotal(Double milkDiscount,Double greaseDiscount, Double solidDiscount){
        double milk = 1.0;
        double grease = 1.0;
        double solid = 1.0;

        if (milkDiscount < 0.0){
            milk = 0.0;
        }
        if (greaseDiscount < 0.0){
            grease = 0.0;
        }
        if (solidDiscount < 0.0){
            solid = 0.0;
        }
        return milk * milkDiscount + grease * greaseDiscount + solid * solidDiscount;
    }

    public Double calcularPago(Double milkTotalPay, Double totalDiscount){
        double pago = 1.0;
        double descuento = 1.0;

        if (milkTotalPay < 0.0){
            pago = 0.0;
        }
        if (totalDiscount < 0.0){
            descuento = 0.0;
        }
        return pago * milkTotalPay - descuento * totalDiscount;
    }

    public Double calcularRetencion(Double retencion, Double monto){
        if (monto > 950000.0) {
            return retencion * monto;
        } else if (monto < 0.0 || retencion < 0.0 || retencion > 1.0) {
            return 0.0;
        } else{
            return 0.0;
        }
    }

    public Double pagoTotal(Double retencion, Double monto){
        if (monto < 0.0 ){
            return 0.0;
        }
        if (retencion < 0.0) {
            return monto;
        }
        return monto - retencion;
    }

    @Generated
    public void pagarPorId(String code) {
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        GreaseAndSolidEntity grasaSolido = greaseAndSolidService.obtenerGrasasSolidosCodigo(code);
        ArrayList<CollectionEntity> acopios = collectionService.obtenerAcopiosCodigo(code);
        
        PayEntity pago = new PayEntity();

        pago.setCode(code);
        pago.setDate(collectionService.obtenerQuincena(collectionService.obtenerAcopios()));
        pago.setCategory(supplierService.obtenerCategoria(proveedor));
        pago.setName(supplierService.obtenerNombre(proveedor));

        pago.setMilk(collectionService.lecheTotal(acopios));
        pago.setMilkDays(collectionService.diasEntregaTotal(acopios));
        pago.setMilkAverage(collectionService.lechePromedio(pago.getMilk()));
        pago.setMilkChanged(registerService.variacionLeche(pago.getMilk(), registerService.obtenerLeche(registerService.obtenerRegistroCodigo(code))));

        pago.setGrease(greaseAndSolidService.obtenerGrasa(grasaSolido));
        pago.setGreaseChanged(registerService.variacionGrasa(pago.getGrease(), registerService.obtenerGrasa(registerService.obtenerRegistroCodigo(code))));

        pago.setSolid(greaseAndSolidService.obtenerSolido(grasaSolido));
        pago.setSolidChanged(registerService.variacionSolido(pago.getSolid(), registerService.obtenerSolido(registerService.obtenerRegistroCodigo(code))));

        pago.setMilkPay(supplierService.pagoCategoria(supplierService.obtenerCategoria(proveedor)) * pago.getMilk());
        pago.setGreasePay(greaseAndSolidService.pagoGrasa(pago.getGrease()) * pago.getMilk());
        pago.setSolidPay(greaseAndSolidService.pagoSolido(pago.getSolid()) * pago.getMilk());

        pago.setFrecuencyBonification(collectionService.bonificacionFrecuencia(acopios) * pago.getMilkPay());
        pago.setMilkDiscount(registerService.descuentoLeche(pago.getMilkChanged()) * pago.getMilkPay());
        pago.setGreaseDiscount(registerService.descuentoGrasa(pago.getGreaseChanged()) * pago.getMilkPay());
        pago.setSolidDiscount(registerService.descuentoSolido(pago.getSolidChanged()) * pago.getMilkPay());

        pago.setMilkTotalPay(pagoTotalLeche(pago.getMilkPay(), pago.getGreasePay(), pago.getSolidPay(), pago.getFrecuencyBonification()));
        pago.setTotalDiscount(descuentoTotal(pago.getMilkDiscount(), pago.getGreaseDiscount(), pago.getSolidDiscount()));
        pago.setPay(calcularPago(pago.getMilkTotalPay(), pago.getTotalDiscount()));

        pago.setRetention(calcularRetencion(supplierService.pagoRetencion(supplierService.obtenerRetencion(proveedor)), pago.getPay()));
        pago.setTotalPay(pagoTotal(pago.getRetention(), pago.getPay()));

        payRepository.save(pago);
    }
}
