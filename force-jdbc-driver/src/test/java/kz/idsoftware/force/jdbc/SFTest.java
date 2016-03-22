package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.InvalidIdFault;
import com.sforce.soap.partner.LimitInfo;
import com.sforce.soap.partner.LimitInfoHeader;
import com.sforce.soap.partner.LoginFault;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.UnexpectedErrorFault;
import org.junit.Test;

import javax.xml.ws.Holder;

public class SFTest {

  String user = "marat@kalibek.kz";
  String password = "HelloWorld123$%";
  String token = "SreaE7lu6BeKdYlJbx9cU7hZ";
  String url = "https://login.salesforce.com/services/Soap/u/36.0";

  @Test
  public void test1() throws ForceException, UnexpectedErrorFault, LoginFault, InvalidIdFault {
    ForceConnection connection = new ForceConnection(url,user,password,token);

    PartnerConnectionBuilder builder = new PartnerConnectionBuilder();
    PartnerConnection partnerConnection = builder
            .url(url)
            .user(user)
            .password(password)
            .token(token)
            .forceConnectionBase(connection)
            .debugIn(true)
            .build();

    String sessionId = connection.getSessionId();

    {
      Holder<LimitInfoHeader> limitInfoHeaderHolder = new Holder<LimitInfoHeader>();
      QueryRunner queryRunner = new QueryRunner(partnerConnection, sessionId);
      QueryResult queryResult = queryRunner.query("SELECT NAME FROM Account");

    }
    {
      Holder<LimitInfoHeader> limitInfoHeaderHolder = new Holder<LimitInfoHeader>();
                           QueryRunner queryRunner = new QueryRunner(partnerConnection, sessionId, limitInfoHeaderHolder);
      QueryResult queryResult = queryRunner.query("SELECT NAME FROM Account");

      LimitInfo limitInfo = limitInfoHeaderHolder.value.getLimitInfo().get(0);
      System.out.println(limitInfo.getType() + " => " + limitInfo.getCurrent());
    }

//    int i = 0;
//    for (SObject sObject : queryResult.getRecords()) {
//      List<Object> any = sObject.getAny();
//      List e = (List) any;
//      List<String> values = new LinkedList<String>();
//      for (Object o : e) {
//        Element el = (Element) o;
//        int count = el.getChildNodes().getLength();
//        for (int j=0;j<count;j++) {
//          Node item = el.getChildNodes().item(j);
//          item.getChildNodes();
//          values.add(item.getNodeName()+":"+item.getTextContent());
//        }
//      }
//      for (String value : values) {
//        System.out.print(value+"\t");
//      }
//      i++;
//      System.out.println(i);
//    }
  }


}
