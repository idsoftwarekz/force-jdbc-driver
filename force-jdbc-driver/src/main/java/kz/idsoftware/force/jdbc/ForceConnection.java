package kz.idsoftware.force.jdbc;

import com.sforce.soap.partner.CallOptions;
import com.sforce.soap.partner.InvalidFieldFault;
import com.sforce.soap.partner.InvalidIdFault;
import com.sforce.soap.partner.InvalidQueryLocatorFault;
import com.sforce.soap.partner.InvalidSObjectFault;
import com.sforce.soap.partner.LimitInfoHeader;
import com.sforce.soap.partner.LoginFault;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.LoginScopeHeader;
import com.sforce.soap.partner.MalformedQueryFault;
import com.sforce.soap.partner.MruHeader;
import com.sforce.soap.partner.PackageVersion;
import com.sforce.soap.partner.PackageVersionHeader;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.PartnerService;
import com.sforce.soap.partner.QueryOptions;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SessionHeader;
import com.sforce.soap.partner.UnexpectedErrorFault;
import kz.idsoftware.force.jdbc.model.ColumnDescription;
import kz.idsoftware.force.jdbc.model.ColumnValue;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class ForceConnection implements Connection {

  private String url;
  private String user;
  private String password;
  private String token;

  private PartnerConnection partnerConnection;
  private SessionHeader sessionHeader;

  ForceConnection(String url, String user, String password, String token) throws SQLException {
    if (url==null || user==null || password==null || token==null) {
      throw new SQLException("URL, user, password or token parameters must not be null");
    }
    this.url = url;
    this.user = user;
    this.password = password;
    this.token = token;

    initializePartnerConnection();
  }

  private void initializePartnerConnection() throws SQLException {
    PartnerService service = new PartnerService();
    partnerConnection = service.getPartnerConnection();

    LoginScopeHeader loginScopeHeader = ForceConnectionUtils.getLoginScopeHeader();

    CallOptions callOptions = ForceConnectionUtils.getCallOptions();

    try {
      LoginResult loginResult = partnerConnection.login(user, password + token, loginScopeHeader, callOptions);

      String serverUrl = loginResult.getServerUrl();

      BindingProvider bp = (BindingProvider) partnerConnection;
      bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serverUrl);

      sessionHeader = new SessionHeader();
      sessionHeader.setSessionId(loginResult.getSessionId());
    } catch (LoginFault loginFault) {
      loginFault.printStackTrace();
    } catch (InvalidIdFault invalidIdFault) {
      throw new SQLException(invalidIdFault.getCause());
    } catch (UnexpectedErrorFault unexpectedErrorFault) {
      throw new SQLException(unexpectedErrorFault.getCause());
    }
  }

  public PartnerConnection getPartnerConnection() {
    return partnerConnection;
  }

  @Override
  public Statement createStatement() throws SQLException {
    ForceStatement statement = new ForceStatement(this);
    return statement;
  }

  @Override
  public PreparedStatement prepareStatement(String s) throws SQLException {
    return null;
  }

  @Override
  public CallableStatement prepareCall(String s) throws SQLException {
    return null;
  }

  @Override
  public String nativeSQL(String s) throws SQLException {
    return null;
  }

  @Override
  public void setAutoCommit(boolean b) throws SQLException {

  }

  @Override
  public boolean getAutoCommit() throws SQLException {
    return false;
  }

  @Override
  public void commit() throws SQLException {

  }

  @Override
  public void rollback() throws SQLException {

  }

  @Override
  public void close() throws SQLException {

  }

  @Override
  public boolean isClosed() throws SQLException {
    return false;
  }

  @Override
  public DatabaseMetaData getMetaData() throws SQLException {
    return null;
  }

  @Override
  public void setReadOnly(boolean b) throws SQLException {

  }

  @Override
  public boolean isReadOnly() throws SQLException {
    return false;
  }

  @Override
  public void setCatalog(String s) throws SQLException {

  }

  @Override
  public String getCatalog() throws SQLException {
    return null;
  }

  @Override
  public void setTransactionIsolation(int i) throws SQLException {

  }

  @Override
  public int getTransactionIsolation() throws SQLException {
    return 0;
  }

  @Override
  public SQLWarning getWarnings() throws SQLException {
    return null;
  }

  @Override
  public void clearWarnings() throws SQLException {

  }

  @Override
  public Statement createStatement(int i, int i1) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String s, int i, int i1) throws SQLException {
    return null;
  }

  @Override
  public CallableStatement prepareCall(String s, int i, int i1) throws SQLException {
    return null;
  }

  @Override
  public Map<String, Class<?>> getTypeMap() throws SQLException {
    return null;
  }

  @Override
  public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

  }

  @Override
  public void setHoldability(int i) throws SQLException {

  }

  @Override
  public int getHoldability() throws SQLException {
    return 0;
  }

  @Override
  public Savepoint setSavepoint() throws SQLException {
    return null;
  }

  @Override
  public Savepoint setSavepoint(String s) throws SQLException {
    return null;
  }

  @Override
  public void rollback(Savepoint savepoint) throws SQLException {

  }

  @Override
  public void releaseSavepoint(Savepoint savepoint) throws SQLException {

  }

  @Override
  public Statement createStatement(int i, int i1, int i2) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String s, int i, int i1, int i2) throws SQLException {
    return null;
  }

  @Override
  public CallableStatement prepareCall(String s, int i, int i1, int i2) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String s, int i) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String s, int[] ints) throws SQLException {
    return null;
  }

  @Override
  public PreparedStatement prepareStatement(String s, String[] strings) throws SQLException {
    return null;
  }

  @Override
  public Clob createClob() throws SQLException {
    return null;
  }

  @Override
  public Blob createBlob() throws SQLException {
    return null;
  }

  @Override
  public NClob createNClob() throws SQLException {
    return null;
  }

  @Override
  public SQLXML createSQLXML() throws SQLException {
    return null;
  }

  @Override
  public boolean isValid(int i) throws SQLException {
    return false;
  }

  @Override
  public void setClientInfo(String s, String s1) throws SQLClientInfoException {

  }

  @Override
  public void setClientInfo(Properties properties) throws SQLClientInfoException {

  }

  @Override
  public String getClientInfo(String s) throws SQLException {
    return null;
  }

  @Override
  public Properties getClientInfo() throws SQLException {
    return null;
  }

  @Override
  public Array createArrayOf(String s, Object[] objects) throws SQLException {
    return null;
  }

  @Override
  public Struct createStruct(String s, Object[] objects) throws SQLException {
    return null;
  }

  public void setSchema(String schema) throws SQLException {

  }

  public String getSchema() throws SQLException {
    return null;
  }

  public void abort(Executor executor) throws SQLException {

  }

  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

  }

  public int getNetworkTimeout() throws SQLException {
    return 0;
  }

  @Override
  public <T> T unwrap(Class<T> aClass) throws SQLException {
    return null;
  }

  @Override
  public boolean isWrapperFor(Class<?> aClass) throws SQLException {
    return false;
  }

  public Map<ColumnDescription,ColumnValue> processForceQuery(String sql) throws SQLException {
    try {
      QueryOptions queryOptions = ForceConnectionUtils.getQueryOptions();
      MruHeader mruHeader = ForceConnectionUtils.getMruHeader();
      PackageVersionHeader packageVersionHeader = ForceConnectionUtils.getPackageVersionHeader();
      Holder<LimitInfoHeader> limitInfoHeaderHolder = new Holder<LimitInfoHeader>();

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
