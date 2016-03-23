package kz.idsoftware.force.jdbc.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

@Category(MyIntegrationTest.class)
public class IntegrationTest1 extends AbstractIntegrationTest {

  @Test
  public void test0() throws ClassNotFoundException, SQLException {
    Class.forName("kz.idsoftware.force.jdbc.ForceDriver");

    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
      System.out.println(drivers.nextElement().getClass());
    }
  }

  @Test
  public void test1() throws ClassNotFoundException, SQLException {
    Class.forName("kz.idsoftware.force.jdbc.ForceDriver");

    System.out.println(url);

    Connection connection = DriverManager.getConnection(url,user,password+token);

    Assert.assertNotNull(connection);
  }

}
