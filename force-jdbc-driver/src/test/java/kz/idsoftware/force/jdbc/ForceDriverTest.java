package kz.idsoftware.force.jdbc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

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

}
