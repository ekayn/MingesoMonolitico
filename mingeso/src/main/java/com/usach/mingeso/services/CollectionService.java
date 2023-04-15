package com.usach.mingeso.services;

import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.repositories.CollectionRepository;
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
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    public ArrayList<CollectionEntity> obtenerAcopios(){
        return (ArrayList<CollectionEntity>) collectionRepository.findAll();
    }

    public void guardarAcopio(String collectionDate, String collectionShift, String collectionSupplier, String collectionMilk){
        CollectionEntity acopio = new CollectionEntity();
        acopio.setCollectionDate(collectionDate);
        acopio.setCollectionShift(collectionShift);
        acopio.setCollectionSupplier(collectionSupplier);
        acopio.setCollectionMilk(collectionMilk);
        collectionRepository.save(acopio);
    }


    private final Logger logg = LoggerFactory.getLogger(CollectionService.class);
    @Generated
    public String guardarCsv(MultipartFile file){
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
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }
    @Generated
    public void cargarCsv(String direccion){
        BufferedReader bf = null;
        collectionRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarAcopio(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
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
