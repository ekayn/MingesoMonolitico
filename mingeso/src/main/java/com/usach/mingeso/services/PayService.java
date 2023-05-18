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

    public ArrayList<PayEntity> obtenerPagos(){
        return (ArrayList<PayEntity>) payRepository.findAll();
    }

    public Double pagoTotalLeche(PayEntity pago){
        return pago.getMilkPay() + pago.getGreasePay() + pago.getSolidPay() + pago.getFrecuencyBonification();
    }

    public Double descuentoTotal(PayEntity pago){
        return pago.getMilkDiscount() + pago.getGreaseDiscount() + pago.getSolidDiscount();
    }

    public Double calcularPago(PayEntity pago){
        return pago.getMilkTotalPay() - pago.getTotalDiscount();
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
        return monto - retencion;
    }

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
        pago.setMilkChanged(registerService.variacionLeche(pago.getMilk(), code));

        pago.setGrease(greaseAndSolidService.obtenerGrasa(grasaSolido));
        pago.setGreaseChanged(registerService.variacionGrasa(pago.getGrease(), code));

        pago.setSolid(greaseAndSolidService.obtenerSolido(grasaSolido));
        pago.setSolidChanged(registerService.variacionSolido(pago.getSolid(), code));

        pago.setMilkPay(supplierService.pagoCategoria(supplierService.obtenerCategoria(proveedor)) * pago.getMilk());
        pago.setGreasePay(greaseAndSolidService.pagoGrasa(pago.getGrease()) * pago.getMilk());
        pago.setSolidPay(greaseAndSolidService.pagoSolido(pago.getSolid()) * pago.getMilk());

        pago.setFrecuencyBonification(collectionService.bonificacionFrecuencia(acopios) * pago.getMilkPay());
        pago.setMilkDiscount(registerService.descuentoLeche(pago.getMilkChanged()) * pago.getMilkPay());
        pago.setGreaseDiscount(registerService.descuentoGrasa(pago.getGreaseChanged()) * pago.getMilkPay());
        pago.setSolidDiscount(registerService.descuentoSolido(pago.getSolidChanged()) * pago.getMilkPay());

        pago.setMilkTotalPay(pagoTotalLeche(pago));
        pago.setTotalDiscount(descuentoTotal(pago));
        pago.setPay(calcularPago(pago));

        pago.setRetention(calcularRetencion(supplierService.pagoRetencion(supplierService.obtenerRetencion(proveedor)), pago.getPay()));
        pago.setTotalPay(pagoTotal(pago.getRetention(), pago.getPay()));

        payRepository.save(pago);
    }
}
