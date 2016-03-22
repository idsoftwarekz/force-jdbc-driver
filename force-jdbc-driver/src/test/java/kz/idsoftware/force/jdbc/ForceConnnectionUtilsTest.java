package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import org.easymock.EasyMock;

import java.util.LinkedList;
import java.util.List;

public class ForceConnnectionUtilsTest {

  public void testGetResultSetFromQueryResult() {
    QueryResult queryResult = EasyMock.mock(QueryResult.class);

    List<SObject> records = new LinkedList<SObject>();
    SObject obj = new SObject();
    obj.getAny();

    EasyMock.expect(queryResult.getRecords()).andReturn(records);
  }
}
