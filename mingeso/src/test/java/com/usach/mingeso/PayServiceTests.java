package com.usach.mingeso;
import com.usach.mingeso.entities.PayEntity;
import com.usach.mingeso.services.PayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PayServiceTests {
    @Autowired
    PayService payService;

    @Test
    void obtenerPagos(){
        assertNotNull(payService.obtenerPagos());
    }

    @Test
    void calcularPagoLecheNormal(){
        assertEquals(payService.pagoTotalLeche(1000.0, 1000.0, 1000.0, 1000.0), 4000.0, 0.0);
    }

    @Test
    void calcularPagoLecheLecheNegativa(){
        assertEquals(payService.pagoTotalLeche(-1000.0, 1000.0, 1000.0, 1000.0), 3000.0, 0.0);
    }

    @Test
    void calcularPagoLecheGrasaNegativa(){
        assertEquals(payService.pagoTotalLeche(1000.0, -1000.0, 1000.0, 1000.0), 3000.0, 0.0);
    }

    @Test
    void calcularPagoLecheSolidoNegativa(){
        assertEquals(payService.pagoTotalLeche(1000.0, 1000.0, -1000.0, 1000.0), 3000.0, 0.0);
    }

    @Test
    void calcularPagoLecheBonificacionFrecuenciaNegativa(){
        assertEquals(payService.pagoTotalLeche(1000.0, 1000.0, 1000.0, -1000.0), 3000.0, 0.0);
    }

    @Test
    void calcularPagoLecheTodoNegativa(){
        assertEquals(payService.pagoTotalLeche(-1000.0, -1000.0, -1000.0, -1000.0), 0.0, 0.0);
    }

    @Test
    void calcularDescuentoNormal(){
        assertEquals(payService.descuentoTotal(1000.0, 1000.0, 1000.0), 3000.0, 0.0);
    }

    @Test
    void calcularDescuentoLecheNegativa(){
        assertEquals(payService.descuentoTotal(-1000.0, 1000.0, 1000.0), 2000.0, 0.0);
    }

    @Test
    void calcularDescuentoGrasaNegativa(){
        assertEquals(payService.descuentoTotal(1000.0, -1000.0, 1000.0), 2000.0, 0.0);
    }

    @Test
    void calcularDescuentoSolidoNegativa(){
        assertEquals(payService.descuentoTotal(1000.0, 1000.0, -1000.0), 2000.0, 0.0);
    }

    @Test
    void calcularDescuentoTodoNegativo(){
        assertEquals(payService.descuentoTotal(-1000.0, -1000.0, -1000.0), 0.0, 0.0);
    }

    @Test
    void calcularPagoNormal(){
        assertEquals(payService.calcularPago(1000.0, 500.0), 500.0, 0.0);
    }

    @Test
    void calcularPagoPagoNegativo(){
        assertEquals(payService.calcularPago(-1000.0, 500.0), -500.0, 0.0);
    }

    @Test
    void calcularPagoDescuentoNegativo(){
        assertEquals(payService.calcularPago(1000.0, -500.0), 1000.0, 0.0);
    }

    @Test
    void calcularPagoTodoNegativo(){
        assertEquals(payService.calcularPago(-1000.0, -500.0), 0.0, 0.0);
    }

    @Test
    void calcularRetencionPagoBajo(){
        assertEquals(payService.calcularRetencion(0.13, 500.0), 0.0, 0.0);
    }

    @Test
    void calcularRetencionPagoAlto(){
        assertEquals(payService.calcularRetencion(0.13, 13000000.0), 1690000.0, 0.0);
    }

    @Test
    void calcularRetencionSinRetencion(){
        assertEquals(payService.calcularRetencion(0.0, 500.0), 0.0, 0.0);
    }

    @Test
    void calcularRetencionMontoNegativo(){
        assertEquals(payService.calcularRetencion(0.0, -500.0), 0.0, 0.0);
    }

    @Test
    void calcularRetencionRetencionNegativa(){
        assertEquals(payService.calcularRetencion(-0.13, 500.0), 0.0, 0.0);
    }

    @Test
    void calcularRetencionTodoNegativo(){
        assertEquals(payService.calcularRetencion(-0.13, -500.0), 0.0, 0.0);
    }

    @Test
    void calcularPagoTotalNormal(){
        assertEquals(payService.pagoTotal(5000.0, 50000.0), 45000.0, 0.0);
    }

    @Test
    void calcularPagoTotalRetencionNegativa(){
        assertEquals(payService.pagoTotal(-5000.0, 50000.0), 50000.0, 0.0);
    }

    @Test
    void calcularPagoTotalMontoNegativo(){
        assertEquals(payService.pagoTotal(5000.0, -50000.0), 0.0, 0.0);
    }

    /*
    @Test
    void crearPago(){
        payService.pagarPorId("01332");
        assertNotNull(payService.obtenerPagos());
        payService.eliminarPago("01332");
    }

    @Test
    void eliminarPago(){
        payService.pagarPorId("01332");
        payService.eliminarPago("01332");
        assertNotNull(payService.obtenerPagos());
    }

    @Test
    void eliminarPagoNoExistente(){
        payService.eliminarPago("01332");
        assertNotNull(payService.obtenerPagos());
    }

     */
}
