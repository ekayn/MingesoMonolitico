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

    public void pagarPorId(String code) {
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        GreaseAndSolidEntity grasaSolido = greaseAndSolidService.obtenerGrasasSolidosCodigo(code);
        RegisterEntity registro = registerService.obtenerRegistroCodigo(code);
        ArrayList<CollectionEntity> acopios = collectionService.obtenerAcopiosCodigo(code);

        PayEntity pago = new PayEntity();

            if (Integer.parseInt(acopios.get(0).getDate().split("/")[2]) <= 15) {
                pago.setDate(acopios.get(0).getDate().split("/")[0] + "/" + acopios.get(0).getDate().split("/")[1] + "/" + "1");
            } else {
                pago.setDate(acopios.get(0).getDate().split("/")[0] + "/" + acopios.get(0).getDate().split("/")[1] + "/" + "2");
            }

            pago.setCode(code);

            pago.setCategory(proveedor.getCategory());
            pago.setName(proveedor.getName());
            double totalLeche = 0.0;
            double totalDias = 0.0;
            double totalTarde = 0.0;
            double totalManana = 0.0;

            for (CollectionEntity acopio : acopios) {
                totalLeche = totalLeche + acopio.getMilk();
                totalDias = totalDias + 1;
                if (acopio.getShift().equals("M")) {
                    totalManana = totalManana + 1;
                } else if (acopio.getShift().equals("T")) {
                    totalTarde = totalTarde + 1;
                }
            }

            pago.setMilkAverage((double) Math.round((totalLeche / 15.0) * 100d) / 100d);
            pago.setMilkDays(totalDias);
            pago.setMilk(totalLeche);

            if (registro.getMilk() == 0) {
                pago.setMilkChanged(100 * totalLeche);
            } else {
                pago.setMilkChanged((double) Math.round((100 * (Math.abs(1 - totalLeche / registro.getMilk()))) * 100d) / 100d);
                if (pago.getMilkChanged() < 100) {
                    pago.setMilkChanged((-1) * pago.getMilkChanged());
                }
            }

            pago.setGrease(grasaSolido.getGrease());

            if (registro.getGrease() == 0) {
                pago.setGreaseChanged(100 * grasaSolido.getGrease());
            } else {
                pago.setGreaseChanged((double) Math.round((100 * (Math.abs(1 - grasaSolido.getGrease() / registro.getGrease()))) * 100d) / 100d);
                if (pago.getGreaseChanged() < 100) {
                    pago.setGreaseChanged((-1) * pago.getGreaseChanged());
                }
            }

            pago.setSolid(grasaSolido.getSolid());

            if (registro.getSolid() == 0) {
                pago.setSolidChanged(100 * grasaSolido.getSolid());
            } else {
                pago.setSolidChanged((double) Math.round((100 * (Math.abs(1 - grasaSolido.getSolid()) / registro.getSolid())) * 100d) / 100d);
                if (pago.getSolidChanged() < 100) {
                    pago.setSolidChanged((-1) * pago.getSolidChanged());
                }
            }

            switch (proveedor.getCategory()) {
                case "A" -> pago.setMilkPay(totalLeche * 700);
                case "B" -> pago.setMilkPay(totalLeche * 550);
                case "C" -> pago.setMilkPay(totalLeche * 400);
                case "D" -> pago.setMilkPay(totalLeche * 250);
            }

            if (grasaSolido.getGrease() <= 20) {
                pago.setGreasePay(totalLeche * 30);
            } else if (grasaSolido.getGrease() <= 45) {
                pago.setGreasePay(totalLeche * 80);
            } else {
                pago.setGreasePay(totalLeche * 120);
            }

            if (grasaSolido.getSolid() <= 7) {
                pago.setSolidPay(totalLeche * -130);
            } else if (grasaSolido.getSolid() <= 18) {
                pago.setSolidPay(totalLeche * -90);
            } else if (grasaSolido.getSolid() <= 35) {
                pago.setSolidPay(totalLeche * 95);
            } else {
                pago.setSolidPay((totalLeche * 150));
            }

            if (totalManana > 10 && totalTarde > 10) {
                pago.setFrecuencyBonification(pago.getMilkPay() * 0.20);
            } else if (totalManana > 10) {
                pago.setFrecuencyBonification(pago.getMilkPay() * 0.12);
            } else if (totalTarde > 10) {
                pago.setFrecuencyBonification(pago.getMilkPay() * 0.8);
            } else {
                pago.setFrecuencyBonification(0.0);
            }

            if (pago.getMilkChanged() >= 0.0) {
                pago.setMilkDiscount(0.0);
            } else if (pago.getMilkChanged() <= -46.0) {
                pago.setMilkDiscount(pago.getMilkPay() * 0.30);
            } else if (pago.getMilkChanged() <= -26.0) {
                pago.setMilkDiscount(pago.getMilkPay() * 0.15);
            } else if (pago.getMilkChanged() <= -9.0) {
                pago.setMilkDiscount(pago.getMilkPay() * 0.7);
            } else {
                pago.setMilkDiscount(0.0);
            }

            if (pago.getGreaseChanged() >= 0.0) {
                pago.setGreaseDiscount(0.0);
            } else if (pago.getGreaseChanged() <= -41.0) {
                pago.setGreaseDiscount(pago.getMilkPay() * 0.30);
            } else if (pago.getGreaseChanged() <= -26.0) {
                pago.setGreaseDiscount(pago.getMilkPay() * 0.20);
            } else if (pago.getGreaseChanged() <= -16.0) {
                pago.setGreaseDiscount(pago.getMilkPay() * 0.12);
            } else {
                pago.setGreaseDiscount(0.0);
            }

            if (pago.getSolidChanged() >= 0.0) {
                pago.setSolidDiscount(0.0);
            } else if (pago.getSolidChanged() <= -36.0) {
                pago.setSolidDiscount(pago.getMilkPay() * 0.45);
            } else if (pago.getSolidChanged() <= -13.0) {
                pago.setSolidDiscount(pago.getMilkPay() * 0.27);
            } else if (pago.getSolidChanged() <= -7.0) {
                pago.setSolidDiscount(pago.getMilkPay() * 0.18);
            } else {
                pago.setSolidDiscount(0.0);
            }

            pago.setMilkTotalPay(pago.getMilkPay() + pago.getGreasePay() + pago.getSolidPay() + pago.getFrecuencyBonification());
            pago.setTotalDiscount(pago.getMilkDiscount() + pago.getGreaseDiscount() + pago.getSolidDiscount());
            pago.setPay(pago.getMilkTotalPay() - pago.getTotalDiscount());

            if (proveedor.getRetention().equals("Si")) {
                if (pago.getPay() > 950000) {
                    pago.setRetention(pago.getPay() * 0.13);
                    pago.setTotalPay(pago.getPay() - pago.getRetention());
                } else if (pago.getPay() < 0.0) {
                    pago.setRetention(0.0);
                    pago.setTotalPay(0.0);
                } else {
                    pago.setRetention(0.0);
                    pago.setTotalPay(pago.getPay());
                }
            } else if (proveedor.getRetention().equals("No")) {
                if (pago.getPay() < 0.0) {
                    pago.setRetention(0.0);
                    pago.setTotalPay(0.0);
                } else {
                    pago.setRetention(0.0);
                    pago.setTotalPay(pago.getPay());
                }
            } else {
                pago.setRetention(0.0);
                pago.setTotalPay(0.0);
            }

            payRepository.save(pago);

    }
}
