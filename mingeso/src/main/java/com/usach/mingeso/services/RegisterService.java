package com.usach.mingeso.services;

import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.entities.RegisterEntity;
import com.usach.mingeso.repositories.RegisterRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RegisterService {
    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    CollectionService collectionService;

    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    public ArrayList<RegisterEntity> obtenerRegistros(){
        return (ArrayList<RegisterEntity>) registerRepository.findAll();
    }

    public RegisterEntity obtenerRegistroCodigo(String code){
        return registerRepository.getReferenceById(code);
    }

    @Generated
    public void actualizarLeche(){
        for (RegisterEntity registro : registerRepository.findAll()){
            registro.setMilk(0.0);
        }
        for (CollectionEntity acopio : collectionService.obtenerAcopios()){
            RegisterEntity registro = registerRepository.getReferenceById(collectionService.obtenerCodigo(acopio));
            registro.setMilk(collectionService.obtenerLeche(acopio)+registro.getMilk());
        }
    }

    @Generated
    public void actualizarGrasaSolido(){
        for (RegisterEntity registro : registerRepository.findAll()){
            registro.setGrease(greaseAndSolidService.obtenerGrasa(greaseAndSolidService.obtenerGrasasSolidosCodigo(registro.getCode())));
            registro.setSolid(greaseAndSolidService.obtenerSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo(registro.getCode())));
        }
    }

    public void guardarRegistro(String registerCode){
        RegisterEntity registro = new RegisterEntity();
        registro.setCode(registerCode);
        registro.setMilk(0.0);
        registro.setGrease(0.0);
        registro.setSolid(0.0);
        registerRepository.save(registro);
    }

    public double variacionLeche(Double lecheAntes, Double lecheAhora){
        if (lecheAntes <= 0.0) {
            return lecheAhora * 100.0;
        } else {
            double variacion = (double) Math.round((100 * (Math.abs(1 - lecheAhora / lecheAntes))) * 100d) / 100d;
            if (variacion < 100.0) {
                return variacion * (-1);
            } else{
                return variacion;
            }
        }
    }

    public double variacionGrasa(Double grasaAntes, Double grasaAhora){
        if (grasaAntes <= 0.0) {
            return grasaAhora * 100.0;
        } else {
            double variacion = (double) Math.round((100 * (Math.abs(1 - grasaAhora / grasaAntes))) * 100d) / 100d;
            if (variacion < 100.0) {
                return variacion * (-1);
            } else{
                return variacion;
            }
        }        
    }

    public double variacionSolido(Double solidoAntes, Double solidoAhora){
        if (solidoAntes <= 0.0) {
            return solidoAhora * 100.0;
        } else {
            double variacion = (double) Math.round((100 * (Math.abs(1 - solidoAhora / solidoAntes))) * 100d) / 100d;
            if (variacion < 100.0) {
                return variacion * (-1);
            } else{
                return variacion;
            }
        }        
    }

    public double descuentoLeche(Double variacion){
        if (variacion > 0.0){
            return 0.0;
        } else if (variacion <= -46.0) {
            return 0.30;
        } else if (variacion <= -26.0){
            return 0.15;
        } else if (variacion <= -9.0){
            return 0.07;
        } else{
            return 0.0;
        }
    }

    public double descuentoGrasa(Double variacion){
        if (variacion > 0.0){
            return 0.0;
        } else if (variacion <= -41.0) {
            return 0.30;
        } else if (variacion <= -26.0){
            return 0.20;
        } else if (variacion <= -16.0){
            return 0.12;
        } else{
            return 0.0;
        }
    }

    public double descuentoSolido(Double variacion){
        if (variacion > 0.0){
            return 0.0;
        } else if (variacion <= -36.0) {
            return 0.45;
        } else if (variacion <= -13.0){
            return 0.27;
        } else if (variacion <= -7.0){
            return 0.18;
        } else{
            return 0.0;
        }
    }

    public double obtenerLeche(RegisterEntity registro){
        return registro.getMilk();
    }

    public double obtenerGrasa(RegisterEntity registro){
        return registro.getGrease();
    }

    public double obtenerSolido(RegisterEntity registro){
        return registro.getSolid();
    }

    public void eliminarRegistro(String id) {
        try{
            registerRepository.deleteById(id);
        }catch(Exception ignore){
        }
    }
}
