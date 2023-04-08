package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.services.SupplierService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @GetMapping("/listar")
    public String listar(Model model){
        ArrayList<SupplierEntity>proveedores = supplierService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "supplierList";
    }
}
