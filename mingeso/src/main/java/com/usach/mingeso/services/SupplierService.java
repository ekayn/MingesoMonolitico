package com.usach.mingeso.services;

import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public ArrayList<SupplierEntity> obtenerProveedores(){
        return (ArrayList<SupplierEntity>) supplierRepository.findAll();
    }

    public boolean existeProveedorPorCodigo(String code){
        return supplierRepository.existsById(code);
    }

    public SupplierEntity obtenerProveedorCodigo(String code){
        return supplierRepository.getReferenceById(code);
    }

    public void guardarProveedor(String supplierName, String supplierCode, String supplierCategory, String supplierRetention){
        SupplierEntity proveedor = new SupplierEntity();
        proveedor.setName(supplierName);
        proveedor.setCode(supplierCode);
        proveedor.setCategory(supplierCategory);
        proveedor.setRetention(supplierRetention);
        supplierRepository.save(proveedor);
    }

    public boolean eliminarProveedor(String id) {
        try{
            supplierRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
}
