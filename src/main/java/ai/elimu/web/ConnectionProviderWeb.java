package ai.elimu.web;

import ai.elimu.util.ConfigHelper;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProviderWeb implements ConnectionProvider {
    private final String url;
    private final String userName;
    private final String password;

    public ConnectionProviderWeb(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, userName, password);
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {}

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
