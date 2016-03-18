package kz.idsoftware.force.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class ForceDriver implements java.sql.Driver {

  private static final String USER = "user";
  private static final String PASSWORD = "password";
  private static final String TOKEN = "token";

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    if (acceptsURL(url)) {
      String user = info.getProperty(USER);
      String password = info.getProperty(PASSWORD);
      String token = info.getProperty(TOKEN);
      return new ForceConnection(url,user,password,token);
    } else {
      throw new SQLException("Wrong url specified");
    }
  }

  /**
   * url format is <i>jdbc:force://...</i>
   * @param url
   * @return true if url satisfies connection string format
   * @throws SQLException
   */
  @Override
  public boolean acceptsURL(String url) throws SQLException {
    if (url==null) {
      return false;
    }
    return url.startsWith("jdbc:force://");
  }

  @Override
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
    return new DriverPropertyInfo[0];
  }

  @Override
  public int getMajorVersion() {
    return 36;
  }

  @Override
  public int getMinorVersion() {
    return 21;
  }

  @Override
  public boolean jdbcCompliant() {
    return false;
  }

  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException();
  }

}
