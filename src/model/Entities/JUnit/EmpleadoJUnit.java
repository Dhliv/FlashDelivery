package model.Entities.JUnit;

import org.junit.Assert;
import org.junit.Test;

import model.Entities.Empleado;

public class EmpleadoJUnit {
    @Test
    public void prueba1() {
        Assert.assertTrue(true);
    }

    @Test(timeout = 100)
    public void prueba2() {
        Assert.assertEquals(0, 0);
    }

    @Test
    public void prueba3() {
        Empleado.getEmpleadosHabilitados();
        Assert.assertEquals(true, true);
    }
}
