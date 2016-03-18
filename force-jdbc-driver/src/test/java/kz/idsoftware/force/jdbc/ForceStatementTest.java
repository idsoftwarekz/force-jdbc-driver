package kz.idsoftware.force.jdbc;

import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForceStatementTest extends AbstractForceTest {

  @Test
  public void testExecuteQueryNotNull() throws SQLException {
    ForceStatement statement = new ForceStatement(null);
    ResultSet resultSet = statement.executeQuery("SELECT Id FROM Account");

    Assert.assertNotNull(resultSet);
    Assert.assertTrue(resultSet instanceof ForceResultSet);
  }

}
