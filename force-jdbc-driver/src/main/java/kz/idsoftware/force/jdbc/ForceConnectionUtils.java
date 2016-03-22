package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.CallOptions;
import com.sforce.soap.partner.LoginScopeHeader;
import com.sforce.soap.partner.MruHeader;
import com.sforce.soap.partner.PackageVersion;
import com.sforce.soap.partner.PackageVersionHeader;
import com.sforce.soap.partner.QueryOptions;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SessionHeader;
import kz.idsoftware.force.jdbc.model.ColumnDescription;
import kz.idsoftware.force.jdbc.model.ColumnValue;

import java.util.Map;

public class ForceConnectionUtils {

  public static CallOptions getCallOptions() {
    CallOptions callOptions = new CallOptions();
    callOptions.setClient("Open Source Salesforce JDBC Driver");
//    callOptions.setReturnFieldDataTypes(false);

    return callOptions;
  }

  public static QueryOptions getQueryOptions() {
    QueryOptions queryOptions = new QueryOptions();
    queryOptions.setBatchSize(1000);
    return queryOptions;
  }

  public static MruHeader getMruHeader() {
    MruHeader mruHeader = new MruHeader();
    mruHeader.setUpdateMru(true);
    return mruHeader;
  }

  public static PackageVersionHeader getPackageVersionHeader() {
    PackageVersionHeader packageVersionHeader = new PackageVersionHeader();
    PackageVersion packageVersion = new PackageVersion();
    packageVersion.setMajorNumber(36);
    packageVersion.setMinorNumber(36);
    packageVersionHeader.getPackageVersions().add(packageVersion);
    return packageVersionHeader;
  }

  public static Map<ColumnDescription, ColumnValue> getResultSetFromQueryResult(QueryResult queryResult) {
    return null;
  }

  public static LoginScopeHeader getLoginScopeHeader() {
    LoginScopeHeader loginScopeHeader = new LoginScopeHeader();
//    loginScopeHeader.setOrganizationId("iD Software");
//    loginScopeHeader.setPortalId("Salesforce JDBC Open Source Driver");
    return loginScopeHeader;
  }

  public static SessionHeader getSessionHeader(String sessionId) {
    SessionHeader sessionHeader = new SessionHeader();
    sessionHeader.setSessionId(sessionId);
    return sessionHeader;
  }
}
