package jdbc;

import java.sql.Connection;

public interface ConnectionPool extends AutoCloseable {
    Connection getConnection();

    boolean releaseConnection(Connection connection);
}
