package com.usach.mingeso;
import com.usach.mingeso.entities.RegisterEntity;
import com.usach.mingeso.services.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceTests {
    @Autowired
    RegisterService registerService;

    @Test
    void guardarRegistro(){
        registerService.guardarRegistro("0000L");
        assertNotNull(registerService.obtenerRegistroCodigo("0000L"));
        registerService.eliminarRegistro("0000L");
    }

    @Test
    void obtenerTodosLosRegistros(){
        registerService.guardarRegistro("0000L");
        assertNotNull(registerService.obtenerRegistros());
        registerService.eliminarRegistro("0000L");
    }

    @Test
    void obtenerRegistroPorCodigo(){
        registerService.guardarRegistro("0000L");
        assertNotNull(registerService.obtenerRegistroCodigo("0000L"));
        registerService.eliminarRegistro("0000L");
    }

    @Test
    void variacionLecheNormal(){
        assertEquals(registerService.variacionLeche(50.0,5.0), -90.0, 0.0);
    }

    @Test
    void variacionLecheNormal2(){
        assertEquals(registerService.variacionLeche(5.0,50.0), 900.0, 0.0);
    }

    @Test
    void variacionLecheNormal3(){
        assertEquals(registerService.variacionLeche(0.0,50.0), 5000.0, 0.0);
    }

    @Test
    void variacionGrasaNormal(){
        assertEquals(registerService.variacionGrasa(50.0,5.0), -90.0, 0.0);
    }

    @Test
    void variacionGrasaNormal2(){
        assertEquals(registerService.variacionGrasa(5.0,50.0), 900.0, 0.0);
    }

    @Test
    void variacionGrasaNormal3(){
        assertEquals(registerService.variacionGrasa(0.0,50.0), 5000.0, 0.0);
    }

    @Test
    void variacionSolidoNormal(){
        assertEquals(registerService.variacionSolido(50.0,5.0), -90.0, 0.0);
    }

    @Test
    void variacionSolidoNormal2(){
        assertEquals(registerService.variacionSolido(5.0,50.0), 900.0, 0.0);
    }

    @Test
    void variacionSolidoNormal3(){
        assertEquals(registerService.variacionSolido(0.0,50.0), 5000.0, 0.0);
    }

    @Test
    void descuentoLecheNormal1(){
        assertEquals(registerService.descuentoLeche(-46.0), 0.3, 0.0);
    }

    @Test
    void descuentoLecheNormal2(){
        assertEquals(registerService.descuentoLeche(-26.0), 0.15, 0.0);
    }

    @Test
    void descuentoLecheNormal3(){
        assertEquals(registerService.descuentoLeche(-9.0), 0.07, 0.0);
    }

    @Test
    void descuentoLecheNormal4(){
        assertEquals(registerService.descuentoLeche(0.0), 0.0, 0.0);
    }

    @Test
    void descuentoLechePositiva(){
        assertEquals(registerService.descuentoLeche(45.0), 0.0, 0.0);
    }

    @Test
    void descuentoGrasaNormal1(){
        assertEquals(registerService.descuentoGrasa(-41.0), 0.30, 0.0);
    }

    @Test
    void descuentoGrasaNormal2(){
        assertEquals(registerService.descuentoGrasa(-26.0), 0.20, 0.0);
    }

    @Test
    void descuentoGrasaNormal3(){
        assertEquals(registerService.descuentoGrasa(-16.0), 0.12, 0.0);
    }

    @Test
    void descuentoGrasaNormal4(){
        assertEquals(registerService.descuentoGrasa(0.0), 0.0, 0.0);
    }

    @Test
    void descuentoGrasaPositiva(){
        assertEquals(registerService.descuentoGrasa(45.0), 0.0, 0.0);
    }

    @Test
    void descuentoSolidoNormal1(){
        assertEquals(registerService.descuentoSolido(-36.0), 0.45, 0.0);
    }

    @Test
    void descuentoSolidoNormal2(){
        assertEquals(registerService.descuentoSolido(-13.0), 0.27, 0.0);
    }

    @Test
    void descuentoSolidoNormal3(){
        assertEquals(registerService.descuentoSolido(-7.0), 0.18, 0.0);
    }

    @Test
    void descuentoSolidoNormal4(){
        assertEquals(registerService.descuentoSolido(0.0), 0.0, 0.0);
    }

    @Test
    void descuentoSolidoPositiva(){
        assertEquals(registerService.descuentoSolido(45.0), 0.0, 0.0);
    }

    @Test
    void obtenerLecheRegistro(){
        RegisterEntity registro = new RegisterEntity();
        registro.setCode("0000L");
        registro.setMilk(45.0);
        registro.setSolid(10.0);
        registro.setGrease(10.0);
        assertEquals(registerService.obtenerLeche(registro), 45.0, 0.0);
    }

    @Test
    void obtenerGrasaRegistro(){
        RegisterEntity registro = new RegisterEntity();
        registro.setCode("0000L");
        registro.setMilk(45.0);
        registro.setSolid(10.0);
        registro.setGrease(10.0);
        assertEquals(registerService.obtenerGrasa(registro), 10.0, 0.0);
    }

    @Test
    void obtenerSolidoRegistro(){
        RegisterEntity registro = new RegisterEntity();
        registro.setCode("0000L");
        registro.setMilk(45.0);
        registro.setSolid(20.0);
        registro.setGrease(10.0);
        assertEquals(registerService.obtenerSolido(registro), 20.0, 0.0);
    }

    @Test
    void eliminarRegistro(){
        registerService.guardarRegistro("0000L");
        registerService.eliminarRegistro("0000L");
        assertNotNull(registerService.obtenerRegistroCodigo("0000L"));
    }

    @Test
    void eliminarRegistroNoExistente(){
        registerService.eliminarRegistro("0000L");
        assertNotNull(registerService.obtenerRegistroCodigo("0000L"));
    }
}
