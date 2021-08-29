package model.Entities.JUnit;

import org.junit.Assert;
import org.junit.Test;

public class EmpleadoJUnit {
    @Test
    public void prueba1() {
        Assert.assertTrue(true);
    }

    @Test(timeout = 100)
    public void prueba2() {
        Assert.assertEquals(0, 0);
    }
}
