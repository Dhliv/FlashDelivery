package model.Entities.JUnit;

import org.junit.Assert;
import org.junit.Test;

import model.Entities.Paquetes;

public class PaqueteJUnit {
    @Test
    public void prueba1() {
        System.out.println(Paquetes.queryPaquetesSede(1));
        Assert.assertTrue(true);
    }

    @Test(timeout = 100)
    public void prueba2() {
        Assert.assertEquals(0, 0);
    }
}
