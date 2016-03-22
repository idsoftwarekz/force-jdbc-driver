package kz.idsoftware.force.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ForceDriverTest {

  String user = "marat@kalibek.kz";
  String password = "HelloWorld123$%";
  String token = "SreaE7lu6BeKdYlJbx9cU7hZ";
  String url = "https://login.salesforce.com/services/Soap/u/36.0";

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
    properties.setProperty("user",user);
    properties.setProperty("password",password);
    properties.setProperty("token",token);

    Connection connection = driver.connect("jdbc:force://host",properties);

    Assert.assertNotNull(connection);
  }

  @Test(expected = SQLException.class)
  public void testConnectInvalid() throws SQLException {
    Properties properties = new Properties();
    properties.setProperty("user",user);
    properties.setProperty("password",password);

    Connection connection = driver.connect("jdbc:notforce://host",properties);
  }

}
