package com.usach.mingeso.services;

import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.entities.RegisterEntity;
import com.usach.mingeso.repositories.RegisterRepository;
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

    public void actualizarLeche(){
        for (RegisterEntity registro : registerRepository.findAll()){
            registro.setMilk(0);
        }
        for (CollectionEntity acopio : collectionService.obtenerAcopios()){
            RegisterEntity registro = registerRepository.getReferenceById(acopio.getSupplier());
            registro.setMilk(acopio.getMilk()+registro.getMilk());
        }
    }

    public void actualizarGrasaSolido(){
        for (RegisterEntity registro : registerRepository.findAll()){
            registro.setGrease(greaseAndSolidService.obtenerGrasasSolidosCodigo(registro.getCode()).getGrease());
            registro.setSolid(greaseAndSolidService.obtenerGrasasSolidosCodigo(registro.getCode()).getSolid());
        }
    }

    public void guardarRegistro(String registerCode){
        RegisterEntity registro = new RegisterEntity();
        registro.setCode(registerCode);
        registro.setMilk(0);
        registro.setGrease(0);
        registro.setSolid(0);
        registerRepository.save(registro);
    }
}
