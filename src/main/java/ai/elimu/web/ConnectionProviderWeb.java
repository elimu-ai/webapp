package ai.elimu.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

@AllArgsConstructor
public class ConnectionProviderWeb implements ConnectionProvider {

  private final String url;
  private final String userName;
  private final String password;

  @Override
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, userName, password);
  }

  @Override
  public void closeConnection(Connection connection) throws SQLException {
  }

  @Override
  public boolean supportsAggressiveRelease() {
    return true;
  }

  @Override
  public boolean isUnwrappableAs(Class aClass) {
    return false;
  }

  @Override
  public <T> T unwrap(Class<T> aClass) {
    return null;
  }
}
