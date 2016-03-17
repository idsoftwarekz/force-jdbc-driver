package kz.idsoftware.force.jdbc;

import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class ForceDriver implements java.sql.Driver {

  @Override
  public Connection connect(String url, Properties info) throws SQLException {
    return null;
  }

  /**
   * url format is <i>jdbc:force://...</i>
   * @param url
   * @return true if url satisfies connection string format
   * @throws SQLException
   */
  @Override
  public boolean acceptsURL(String url) throws SQLException {
    return false;
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

  @Override
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    return null;
  }

}
