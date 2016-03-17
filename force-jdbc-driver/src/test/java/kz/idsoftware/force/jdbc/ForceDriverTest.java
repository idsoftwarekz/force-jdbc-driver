package kz.idsoftware.force.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ForceDriverTest {

  private java.sql.Driver driver;

  @Before
  public void setup() {
    driver = new ForceDriver();
  }

  @Test
  public void testAcceptsURL() throws SQLException {
    // Valid
    Assert.assertTrue(driver.acceptsURL("jdbc:force://somehost"));

    // Invalid
    Assert.assertFalse(driver.acceptsURL("jdbc:notforce://somehost"));

    // Invalid
    Assert.assertFalse(driver.acceptsURL(null));
  }

  @Test
  public void testConnectValid() throws SQLException {
    Properties properties = new Properties();
    properties.setProperty("user","user");
    properties.setProperty("password","password");

    Connection connection = driver.connect("jdbc:force://host",properties);

    Assert.assertNotNull(connection);
  }

  @Test(expected = SQLException.class)
  public void testConnectInvalid() throws SQLException {
    Properties properties = new Properties();
    properties.setProperty("user","user");
    properties.setProperty("password","password");

    Connection connection = driver.connect("jdbc:notforce://host",properties);
  }

}
