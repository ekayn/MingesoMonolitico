package com.usach.mingeso;

import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.services.CollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollectionServiceTests {
    @Autowired
    CollectionService collectionService;

    @Test
    void guardarAcopio1(){
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 1.0);
        assertNotNull(collectionService.obtenerAcopiosCodigo("0000L"));
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void guardarAcopioLecheNegativa(){
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", -1.0);
        assertEquals(collectionService.obtenerAcopiosCodigo("0000L").get(0).getMilk(), 0.0,0.0);
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void guardarAcopioTurnoNoValido(){
        collectionService.guardarAcopio("2020/02/11", "NULL", "0000L", -1.0);
        assertEquals(collectionService.obtenerAcopiosCodigo("0000L").get(0).getShift(), "M");
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void obtenerTodosLosAcopios(){
        collectionService.guardarAcopio("2020/02/11", "M", "0000L", 1.0);
        assertNotNull(collectionService.obtenerAcopios());
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void obtenerAcopiosPorCodigo(){
        collectionService.guardarAcopio("2020/02/11", "M", "0000L", 1.0);
        assertNotNull(collectionService.obtenerAcopiosCodigo("0000L"));
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void calcularBonificacionPorFrecuenciaSinBonificacion(){
        collectionService.guardarAcopio("2020/02/01", "M", "0000L", 1.0);
        assertEquals(collectionService.bonificacionFrecuencia(collectionService.obtenerAcopiosCodigo("0000L")), 0.0, 0.0);
        for (CollectionEntity acopio : collectionService.obtenerAcopiosCodigo("0000L")){
            collectionService.eliminarAcopio(acopio);
        }
    }

    @Test
    void calcularBonificacionPorFrecuenciaMannana(){
        collectionService.guardarAcopio("2020/02/01", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/02", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/03", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/04", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/05", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/06", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/07", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/08", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/09", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/10", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/11", "M", "0000L", 1.0);
        assertEquals(collectionService.bonificacionFrecuencia(collectionService.obtenerAcopiosCodigo("0000L")), 0.12, 0.0);
        for (CollectionEntity acopio : collectionService.obtenerAcopiosCodigo("0000L")){
            collectionService.eliminarAcopio(acopio);
        }

    }

    @Test
    void calcularBonificacionPorFrecuenciaTarde(){
        collectionService.guardarAcopio("2020/02/01", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/02", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/03", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/04", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/05", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/06", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/07", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/08", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/09", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/10", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 1.0);
        assertEquals(collectionService.bonificacionFrecuencia(collectionService.obtenerAcopiosCodigo("0000L")), 0.08, 0.0);
        for (CollectionEntity acopio : collectionService.obtenerAcopiosCodigo("0000L")){
            collectionService.eliminarAcopio(acopio);
        }
    }

    @Test
    void calcularBonificacionPorFrecuenciaMannanaTarde(){
        collectionService.guardarAcopio("2020/02/01", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/02", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/03", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/04", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/05", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/06", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/07", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/08", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/09", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/10", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/11", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/01", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/02", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/03", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/04", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/05", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/06", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/07", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/08", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/09", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/10", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 1.0);
        assertEquals(collectionService.bonificacionFrecuencia(collectionService.obtenerAcopiosCodigo("0000L")), 0.20, 0.0);
        for (CollectionEntity acopio : collectionService.obtenerAcopiosCodigo("0000L")){
            collectionService.eliminarAcopio(acopio);
        }
    }

    @Test
    void calcularLecheTotalSinAcopios(){
        assertEquals(collectionService.lecheTotal(collectionService.obtenerAcopiosCodigo("0000L")), 0.0, 0.0);
        for (CollectionEntity acopio : collectionService.obtenerAcopiosCodigo("0000L")){
            collectionService.eliminarAcopio(acopio);
        }
    }

    @Test
    void calcularLecheTotalConAcopios(){
        collectionService.guardarAcopio("2020/02/01", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/02", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/03", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/04", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/05", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/06", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/07", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/08", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/09", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/10", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/11", "M", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/01", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/02", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/03", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/04", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/05", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/06", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/07", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/08", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/09", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/10", "T", "0000L", 1.0);
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 1.0);
        assertEquals(collectionService.lecheTotal(collectionService.obtenerAcopiosCodigo("0000L")), 22.0, 0.0);
        for (CollectionEntity acopio : collectionService.obtenerAcopiosCodigo("0000L")){
            collectionService.eliminarAcopio(acopio);
        }
    }

    @Test
    void obtenerFechaConAcopios(){
        assertEquals(collectionService.obtenerQuincena(collectionService.obtenerAcopios()), "2023/03/02");
    }

    @Test
    void obtenerFechaSinAcopios(){
        ArrayList<CollectionEntity> acopios = new ArrayList<>();
        assertEquals(collectionService.obtenerQuincena(acopios), "####/##/##");
    }

    @Test
    void calcularLechePromedio(){
        assertEquals(collectionService.lechePromedio(3.0), 0.2, 0.0);
    }

    @Test
    void calcularLechePromedioValorNegativo(){
        assertEquals(collectionService.lechePromedio(-3.0), 0.2, 0.0);
    }

    @Test
    void calcularDiasDeEntrega(){
        assertEquals(collectionService.diasEntregaTotal(collectionService.obtenerAcopios()), collectionService.obtenerAcopios().size(), 0.0);
    }

    @Test
    void obtenerCodigoDeAcopio(){
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 1.0);
        assertEquals(collectionService.obtenerCodigo(collectionService.obtenerAcopiosCodigo("0000L").get(0)), "0000L");
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void obtenerLecheDeAcopio(){
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 12.0);
        assertEquals(collectionService.obtenerLeche(collectionService.obtenerAcopiosCodigo("0000L").get(0)), 12.0, 0.0);
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
    }

    @Test
    void eliminarUnAcopio(){
        collectionService.guardarAcopio("2020/02/11", "T", "0000L", 1.0);
        collectionService.eliminarAcopio(collectionService.obtenerAcopiosCodigo("0000L").get(0));
        assertTrue(collectionService.obtenerAcopiosCodigo("0000L").isEmpty());
    }
}
