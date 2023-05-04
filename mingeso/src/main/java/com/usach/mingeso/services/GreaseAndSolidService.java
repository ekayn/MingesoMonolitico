package com.usach.mingeso.services;

import com.usach.mingeso.entities.GreaseAndSolidEntity;
import com.usach.mingeso.repositories.GreaseAndSolidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Generated;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GreaseAndSolidService {
    @Autowired
    GreaseAndSolidRepository greaseAndSolidRepository;

    public ArrayList<GreaseAndSolidEntity> obtenerGrasasYSolidos(){
        return (ArrayList<GreaseAndSolidEntity>) greaseAndSolidRepository.findAll();
    }

    public void guardarGrasaYSolido(String greaseAndSolidCode, Integer greaseAndSolidGrease, Integer greaseAndSolidSolid){
        GreaseAndSolidEntity grasaYSolido = new GreaseAndSolidEntity();
        grasaYSolido.setCode(greaseAndSolidCode);
        grasaYSolido.setGrease(greaseAndSolidGrease);
        grasaYSolido.setSolid(greaseAndSolidSolid);
        greaseAndSolidRepository.save(grasaYSolido);
    }

    public GreaseAndSolidEntity obtenerGrasasSolidosCodigo(String code){
        return greaseAndSolidRepository.getReferenceById(code);
    }

    private final Logger logg = LoggerFactory.getLogger(CollectionService.class);
    @Generated
    public void guardarCsv(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
    @Generated
    public void cargarCsv(String direccion){
        BufferedReader bf = null;
        greaseAndSolidRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarGrasaYSolido(bfRead.split(";")[0], Integer.parseInt(bfRead.split(";")[1]), Integer.parseInt(bfRead.split(";")[2]));
                }
            }
            System.out.println("ARCHIVO CARGADO EXITOSAMENTE");
        }catch(Exception e){
            System.err.println("ERROR AL CARGAR EL ARCHIVO");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
}
