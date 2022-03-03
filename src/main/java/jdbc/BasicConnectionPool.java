package jdbc;

import jdbc.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BasicConnectionPool implements ConnectionPool {
    private static final String URL_KEY = "db.url";
    private static final String USER_NAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final int POOL_SIZE = 10;

    private static BlockingQueue<Connection> connectionPool;
    private static List<Connection> usedConnections = new ArrayList<>();

    static {
        driveLoader();
    }

    private static void driveLoader() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static BasicConnectionPool connectPool() {
        BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return new BasicConnectionPool(pool);
    }

    public BasicConnectionPool(BlockingQueue<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() {
        Connection connection;
        try {
            connection = connectionPool.take();
            releaseConnection(connection);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getKey(URL_KEY),
                    PropertiesUtil.getKey(USER_NAME_KEY),
                    PropertiesUtil.getKey(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        try {
            for (Connection connection : usedConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
}

//class Runner {
//    public static void main(String[] args) {
//        try (Connection connection = BasicConnectionPool.connectPool().getConnection())
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}