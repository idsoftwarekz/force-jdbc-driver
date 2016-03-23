package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.InvalidFieldFault;
import com.sforce.soap.partner.InvalidIdFault;
import com.sforce.soap.partner.InvalidQueryLocatorFault;
import com.sforce.soap.partner.InvalidSObjectFault;
import com.sforce.soap.partner.LimitInfoHeader;
import com.sforce.soap.partner.LoginFault;
import com.sforce.soap.partner.MalformedQueryFault;
import com.sforce.soap.partner.MruHeader;
import com.sforce.soap.partner.PackageVersionHeader;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryOptions;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SessionHeader;
import com.sforce.soap.partner.UnexpectedErrorFault;
import kz.idsoftware.force.jdbc.model.ColumnDescription;
import kz.idsoftware.force.jdbc.model.ColumnValue;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.Map;

public class ForceConnectionBase {

  private PartnerConnection partnerConnection;
  private String sessionId;

  public ForceConnectionBase(String url, String user, String password, String token) throws ForceException {
    try {
      PartnerConnectionBuilder builder = new PartnerConnectionBuilder();
      builder = builder
//              .url(url)
              .url("https://login.salesforce.com/services/Soap/u/36.0")
              .user(user)
              .password(password)
              .forceConnectionBase(this);
      if (token!=null) {
        builder = builder.token(token);
      }
      partnerConnection = builder.build();
    } catch (UnexpectedErrorFault unexpectedErrorFault) {
      throw new ForceException(unexpectedErrorFault);
    } catch (LoginFault loginFault) {
      throw new ForceException(loginFault);
    } catch (InvalidIdFault invalidIdFault) {
      throw new ForceException(invalidIdFault);
    }
  }

  public PartnerConnection getPartnerConnection() {
    return partnerConnection;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Map<ColumnDescription,ColumnValue> processForceQuery(String sql) throws SQLException {

    QueryRunner queryRunner = new QueryRunner(partnerConnection, sessionId);
    try {
      QueryOptions queryOptions = ForceConnectionUtils.getQueryOptions();
      MruHeader mruHeader = ForceConnectionUtils.getMruHeader();
      PackageVersionHeader packageVersionHeader = ForceConnectionUtils.getPackageVersionHeader();
      Holder<LimitInfoHeader> limitInfoHeaderHolder = new Holder<LimitInfoHeader>();
      SessionHeader sessionHeader = ForceConnectionUtils.getSessionHeader(sessionId);

      QueryResult queryResult = partnerConnection.query(
              sql,
              sessionHeader,
              ForceConnectionUtils.getCallOptions(),
              queryOptions,
              mruHeader,
              packageVersionHeader,
              limitInfoHeaderHolder);

      return ForceConnectionUtils.getResultSetFromQueryResult(queryResult);
    } catch (MalformedQueryFault malformedQueryFault) {
      throw new SQLException(malformedQueryFault.getCause());
    } catch (InvalidIdFault invalidIdFault) {
      throw new SQLException(invalidIdFault.getCause());
    } catch (UnexpectedErrorFault unexpectedErrorFault) {
      throw new SQLException(unexpectedErrorFault.getCause());
    } catch (InvalidQueryLocatorFault invalidQueryLocatorFault) {
      throw new SQLException(invalidQueryLocatorFault.getCause());
    } catch (InvalidFieldFault invalidFieldFault) {
      throw new SQLException(invalidFieldFault.getCause());
    } catch (InvalidSObjectFault invalidSObjectFault) {
      throw new SQLException(invalidSObjectFault.getCause());
    }
  }
}
