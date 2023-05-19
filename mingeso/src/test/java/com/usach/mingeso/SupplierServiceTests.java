package com.usach.mingeso;
import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.services.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SupplierServiceTests {
    @Autowired
    SupplierService supplierService;

    @Test
    void guardarUnProveedorNormal(){
        supplierService.guardarProveedor("EmpresaGenerica", "0000L", "A", "No");
        assertNotNull(supplierService.obtenerProveedorCodigo("0000L"));
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
    }

    @Test
    void obtenerTodosLosProveedores(){
        supplierService.guardarProveedor("EmpresaGenerica", "0000L", "A", "Si");
        assertNotNull(supplierService.obtenerProveedores());
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
    }

    @Test
    void existeProveedorPorCodigo(){
        supplierService.guardarProveedor("EmpresaGenerica", "0000L", "A", "NULL");
        assertTrue(supplierService.existeProveedorPorCodigo("0000L"));
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
    }

    @Test
    void existeProveedorPorNombre(){
        supplierService.guardarProveedor("EmpresaGenerica", "0000L", "A", "NULL");
        assertTrue(supplierService.existeProveedorPorNombre("EmpresaGenerica"));
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
    }

    @Test
    void obtenerProveedorPorCodigo(){
        supplierService.guardarProveedor("EmpresaGenerica", "0000L", "A", "Si");
        assertNotNull(supplierService.obtenerProveedorCodigo("0000L"));
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
    }

    @Test
    void pagoCategoriaNormal1(){
        assertEquals(supplierService.pagoCategoria("A"), 700.0, 0.0);
    }

    @Test
    void pagoCategoriaNormal2(){
        assertEquals(supplierService.pagoCategoria("B"), 550.0, 0.0);
    }

    @Test
    void pagoCategoriaNormal3(){
        assertEquals(supplierService.pagoCategoria("C"), 400.0, 0.0);
    }

    @Test
    void pagoCategoriaNormal4(){
        assertEquals(supplierService.pagoCategoria("D"), 250.0, 0.0);
    }

    @Test
    void pagoCategoriaErronea(){
        assertEquals(supplierService.pagoCategoria("NULL"), 0.0, 0.0);
    }

    @Test
    void pagoRetencionNormal1(){
        assertEquals(supplierService.pagoRetencion("Si"), 0.13, 0.0);
    }

    @Test
    void pagoRetencionNormal2(){
        assertEquals(supplierService.pagoRetencion("No"), 0.0, 0.0);
    }

    @Test
    void pagoRetencionErroena(){
        assertEquals(supplierService.pagoRetencion("NULL"), 0.0, 0.0);
    }


    @Test
    void obtenerNombreProveedor(){
        SupplierEntity proveedor = new SupplierEntity();
        proveedor.setCode("0000L");
        proveedor.setName("EmpresaGenerica");
        proveedor.setCategory("A");
        proveedor.setRetention("No");
        assertEquals(supplierService.obtenerNombre(proveedor), "EmpresaGenerica");
    }

    @Test
    void obtenerCategoriaProveedor(){
        SupplierEntity proveedor = new SupplierEntity();
        proveedor.setCode("0000L");
        proveedor.setName("EmpresaGenerica");
        proveedor.setCategory("A");
        proveedor.setRetention("No");
        assertEquals(supplierService.obtenerCategoria(proveedor), "A");
    }

    @Test
    void obtenerRetencionProveedor(){
        SupplierEntity proveedor = new SupplierEntity();
        proveedor.setCode("0000L");
        proveedor.setName("EmpresaGenerica");
        proveedor.setCategory("A");
        proveedor.setRetention("No");
        assertEquals(supplierService.obtenerRetencion(proveedor), "No");
    }

    @Test
    void eliminarProveedorPorCodigoExistente(){
        supplierService.guardarProveedor("EmpresaGenerica", "0000L", "A", "Si");
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
        assertNotNull(supplierService.obtenerProveedorCodigo("0000L"));
    }

    @Test
    void eliminarProveedorPorCodigoNoExistente(){
        supplierService.eliminarProveedor(supplierService.obtenerProveedorCodigo("0000L").getCode());
        assertNotNull(supplierService.obtenerProveedorCodigo("0000L"));
    }
}
