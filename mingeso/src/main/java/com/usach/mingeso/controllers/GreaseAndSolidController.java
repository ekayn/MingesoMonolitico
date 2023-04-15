package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.entities.GreaseAndSolidEntity;
import com.usach.mingeso.services.CollectionService;
import com.usach.mingeso.services.GreaseAndSolidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
@RequestMapping
public class GreaseAndSolidController {
    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    @GetMapping("/listar-grasas-solidos")
    public String listarGrasasSolidos(Model model){
        ArrayList<GreaseAndSolidEntity> grasasYSolidos = greaseAndSolidService.obtenerGrasasYSolidos();
        model.addAttribute("grasasYSolidos", grasasYSolidos);
        return "GreaseAndSolidList";
    }

    @GetMapping("/subir-grasas-solidos")
    public String subirGrasasSolidos(){
        return "GreaseAndSolidImport";
    }

    @PostMapping("/subir-grasas-solidos")
    public String guardarGrasasSolidos(@RequestParam("file") MultipartFile file) {
        greaseAndSolidService.guardarCsv(file);
        greaseAndSolidService.cargarCsv(file.getOriginalFilename());
        return "redirect:/subir-grasas-solidos";
    }
}
