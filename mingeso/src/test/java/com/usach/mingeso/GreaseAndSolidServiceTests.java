package com.usach.mingeso;
import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.entities.GreaseAndSolidEntity;
import com.usach.mingeso.services.GreaseAndSolidService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GreaseAndSolidServiceTests {
    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    @Test
    void guardarGrasaSolidoNormal(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", 45.0,  45.0);
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void guardarGrasaSolidoGrasaNegativa(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", -45.0,  45.0);
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void guardarGrasaSolidoSolidoNegativo(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", 45.0,  -45.0);
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void guardarGrasaSolidoGrasaYSolidoNegativo(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", -45.0,  -45.0);
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void obtenerTodosLosGrasasSolidos(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", 45.0, 45.0);
        assertNotNull(greaseAndSolidService.obtenerGrasasYSolidos());
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void obtenerGrasaSolidoPorCodigo(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", 45.0, 45.0);
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void existeGrasasSolidosPorCodigo(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", 45.0, 45.0);
        assertTrue(greaseAndSolidService.existeGrasaSolidoCodigo("0000L"));
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void calcularPagoGrasaNormal1(){
        assertEquals(greaseAndSolidService.pagoGrasa(0.0), 30.0, 0.0);
    }

    @Test
    void calcularPagoGrasaNormal2(){
        assertEquals(greaseAndSolidService.pagoGrasa(21.0), 80.0, 0.0);
    }

    @Test
    void calcularPagoGrasaNormal3(){
        assertEquals(greaseAndSolidService.pagoGrasa(46.0), 120.0, 0.0);
    }

    @Test
    void calcularPagoGrasaMuyAlta(){
        assertEquals(greaseAndSolidService.pagoGrasa(100000.0), 120.0, 0.0);
    }

    @Test
    void calcularPagoGrasaBajoCero(){
        assertEquals(greaseAndSolidService.pagoGrasa(-46.0), 0.0, 0.0);
    }

    @Test
    void calcularPagoSolidoNormal1(){
        assertEquals(greaseAndSolidService.pagoSolido(0.0), -130.0, 0.0);
    }

    @Test
    void calcularPagoSolidoNormal2(){
        assertEquals(greaseAndSolidService.pagoSolido(8.0), -90.0, 0.0);
    }

    @Test
    void calcularPagoSolidoNormal3(){
        assertEquals(greaseAndSolidService.pagoSolido(19.0), 95.0, 0.0);
    }

    @Test
    void calcularPagoSolidoNormal4(){
        assertEquals(greaseAndSolidService.pagoSolido(36.0), 150.0, 0.0);
    }

    @Test
    void calcularPagoSolidoMuyAlta(){
        assertEquals(greaseAndSolidService.pagoSolido(100000.0), 150.0, 0.0);
    }

    @Test
    void calcularPagoSolidoBajoCero(){
        assertEquals(greaseAndSolidService.pagoSolido(-46.0), 0.0, 0.0);
    }


    @Test
    void obtenerLaGrasa(){
        GreaseAndSolidEntity grasaSolido = new GreaseAndSolidEntity();
        grasaSolido.setCode("0000L");
        grasaSolido.setGrease(12.0);
        grasaSolido.setSolid(10.0);
        assertEquals(greaseAndSolidService.obtenerGrasa(grasaSolido), 12.0, 0.0);
    }

    @Test
    void obtenerElSolido(){
        GreaseAndSolidEntity grasaSolido = new GreaseAndSolidEntity();
        grasaSolido.setCode("0000L");
        grasaSolido.setGrease(12.0);
        grasaSolido.setSolid(10.0);
        assertEquals(greaseAndSolidService.obtenerSolido(grasaSolido), 10.0, 0.0);
    }

    @Test
    void eliminarGrasaYSolido(){
        greaseAndSolidService.guardarGrasaYSolido("0000L", 45.0, 45.0);
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

    @Test
    void eliminarGrasaYSolidoCodigoNoExistente(){
        greaseAndSolidService.eliminarGrasaSolido(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
        assertNotNull(greaseAndSolidService.obtenerGrasasSolidosCodigo("0000L"));
    }

}
    