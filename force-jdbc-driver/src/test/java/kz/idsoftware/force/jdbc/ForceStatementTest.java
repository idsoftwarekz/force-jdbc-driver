package kz.idsoftware.force.jdbc;

import kz.idsoftware.force.jdbc.model.ColumnDescription;
import kz.idsoftware.force.jdbc.model.ColumnValue;
import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ForceStatementTest extends AbstractForceTest {

  private ForceConnection forceConnection;

  @Before
  public void setup() {
    forceConnection = EasyMock.mock(ForceConnection.class);
  }

  @Test
  public void testExecuteQuery() throws SQLException {
    EasyMock.expect(forceConnection.processForceQuery("SELECT Id FROM Account")).andReturn(new HashMap<ColumnDescription,ColumnValue>()).times(1);

    EasyMock.replay(forceConnection);

    ForceStatement statement = new ForceStatement(forceConnection);

    ResultSet resultSet = statement.executeQuery("SELECT Id FROM Account");

    EasyMock.verify(forceConnection);
  }

  @Test
  public void testExecuteQueryGetData() throws SQLException {
    ForceStatement statement = new ForceStatement(forceConnection);

  }
}
