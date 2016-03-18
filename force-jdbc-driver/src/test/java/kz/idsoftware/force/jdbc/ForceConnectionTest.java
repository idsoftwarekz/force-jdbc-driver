package kz.idsoftware.force.jdbc;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

public class ForceConnectionTest {

  @Test
  public void testValidConnection() throws SQLException {
    ForceConnection connection = new ForceConnection("url","user","password","token");

    Assert.assertNotNull(connection);
  }

  @Test(expected = SQLException.class)
  public void testInvalidConnection() throws SQLException {
    ForceConnection connection = new ForceConnection(null,"user","password","token");
  }

  @Test
  public void testCreateStatement() throws SQLException {
    ForceConnection connection = new ForceConnection("url","user","password","token");
    Statement statement = connection.createStatement();

    Assert.assertNotNull(statement);
    Assert.assertTrue(statement instanceof ForceStatement);
  }

}
