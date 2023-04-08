package com.usach.mingeso.services;

import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public ArrayList<SupplierEntity> obtenerProveedores(){
        return (ArrayList<SupplierEntity>) supplierRepository.findAll();
    }

    public  SupplierEntity guardarProveedor(SupplierEntity proveedor){
        return supplierRepository.save(proveedor);
    }

    public Optional<SupplierEntity> obtenerProveedorPorId(Long id){
        return supplierRepository.findById(id);
    }

    public boolean eliminarProveedor(Long id) {
        try{
            supplierRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
}
