package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.CallOptions;
import com.sforce.soap.partner.InvalidFieldFault;
import com.sforce.soap.partner.InvalidIdFault;
import com.sforce.soap.partner.InvalidQueryLocatorFault;
import com.sforce.soap.partner.InvalidSObjectFault;
import com.sforce.soap.partner.LimitInfoHeader;
import com.sforce.soap.partner.MalformedQueryFault;
import com.sforce.soap.partner.MruHeader;
import com.sforce.soap.partner.PackageVersionHeader;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryOptions;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SessionHeader;
import com.sforce.soap.partner.UnexpectedErrorFault;

import javax.xml.ws.Holder;

public class QueryRunner {

  private PartnerConnection partnerConnection;
  private String sql;
  private String sessionId;
  private CallOptions callOptions = ForceConnectionUtils.getCallOptions();
  private QueryOptions queryOptions = ForceConnectionUtils.getQueryOptions();
  private MruHeader mruHeader = ForceConnectionUtils.getMruHeader();
  private PackageVersionHeader packageVersionHeader = ForceConnectionUtils.getPackageVersionHeader();
  private Holder<LimitInfoHeader> limitInfoHeaderHolder;

  public QueryRunner(PartnerConnection partnerConnection, String sessionId) {
    this(partnerConnection, sessionId, new Holder<LimitInfoHeader>());
  }

  public QueryRunner(PartnerConnection partnerConnection, String sessionId, Holder<LimitInfoHeader> limitInfoHeaderHolder) {
    this.partnerConnection = partnerConnection;
    this.sessionId = sessionId;
    this.limitInfoHeaderHolder = limitInfoHeaderHolder;
  }

  public QueryRunner limitInfoHeaderHolder(Holder<LimitInfoHeader> limitInfoHeaderHolder) {
    this.limitInfoHeaderHolder = limitInfoHeaderHolder;
    return this;
  }

  public QueryRunner callOptions(CallOptions callOptions) {
    this.callOptions = callOptions;
    return this;
  }

  public QueryRunner mruHeader(MruHeader mruHeader) {
    this.mruHeader = mruHeader;
    return this;
  }

  public QueryRunner packageVersionHeader(PackageVersionHeader packageVersionHeader) {
    this.packageVersionHeader = packageVersionHeader;
    return this;
  }

  public QueryResult query(String sql) throws ForceException {
    try {
      SessionHeader sessionHeader = new SessionHeader();
      sessionHeader.setSessionId(sessionId);
      QueryResult queryResult = partnerConnection.query(
              sql,
              sessionHeader,
              ForceConnectionUtils.getCallOptions(),
              queryOptions,
              mruHeader,
              packageVersionHeader,
              limitInfoHeaderHolder);
      return queryResult;
    } catch (MalformedQueryFault malformedQueryFault) {
      throw new ForceException(malformedQueryFault);
    } catch (InvalidIdFault invalidIdFault) {
      throw new ForceException(invalidIdFault);
    } catch (UnexpectedErrorFault unexpectedErrorFault) {
      throw new ForceException(unexpectedErrorFault);
    } catch (InvalidQueryLocatorFault invalidQueryLocatorFault) {
      throw new ForceException(invalidQueryLocatorFault);
    } catch (InvalidFieldFault invalidFieldFault) {
      throw new ForceException(invalidFieldFault);
    } catch (InvalidSObjectFault invalidSObjectFault) {
      throw new ForceException(invalidSObjectFault);
    }
  }

}
