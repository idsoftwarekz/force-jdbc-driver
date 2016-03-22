package kz.idsoftware.force.jdbc;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

public class ForceConnectionTest {

  String user = "marat@kalibek.kz";
  String password = "HelloWorld123$%";
  String token = "SreaE7lu6BeKdYlJbx9cU7hZ";
  String url = "https://login.salesforce.com/services/Soap/u/36.0";

  @Test
  public void testValidConnection() throws ForceException {
    ForceConnection connection = new ForceConnection(url,user,password,token);

    Assert.assertNotNull(connection);
  }

//  @Test(expected = SQLException.class)
//  public void testInvalidConnection() throws ForceException {
//    ForceConnection connection = new ForceConnection(null,user,password,token);
//  }

  @Test
  public void testCreateStatement() throws SQLException, ForceException {
    ForceConnection connection = new ForceConnection(url,user,password,token);
    Statement statement = connection.createStatement();

    Assert.assertNotNull(statement);
    Assert.assertTrue(statement instanceof ForceStatement);
  }

}
