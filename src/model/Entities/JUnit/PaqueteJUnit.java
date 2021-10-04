package model.Entities.JUnit;

import org.junit.Assert;
import org.junit.Test;

import model.Entities.Paquete;

public class PaqueteJUnit {
  @Test
  public void prueba1() {
    // System.out.println(Paquete.queryPaquetesSede(1));
    Assert.assertTrue(true);
  }

  @Test(timeout = 100)
  public void prueba2() {
    Assert.assertEquals(0, 0);
  }
}
