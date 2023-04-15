package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.services.SupplierService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @GetMapping("/listar-proveedores")
    public String listarProveedores(Model model){
        ArrayList<SupplierEntity>proveedores = supplierService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "supplierList";
    }

    @GetMapping("/ingresar-proveedor")
    public String proveedor(){
        return "supplierAdd";
    }
    @PostMapping("/ingresar-proveedor")
    public String nuevoProveedor(@RequestParam("supplierName") String supplierName,
                                 @RequestParam("supplierCode") String supplierCode,
                                 @RequestParam("supplierCategory") String supplierCategory,
                                 @RequestParam("supplierRetention") String supplierRetention){
        supplierService.guardarProveedor(supplierName, supplierCode, supplierCategory, supplierRetention);
        return "redirect:/listar-proveedores";
    }
}
