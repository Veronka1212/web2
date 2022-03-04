package jdbc;

import dao.ApplicationDAOimpl;
import jdbc.util.PropertiesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BasicConnectionPool implements ConnectionPool {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);
    private static final String URL_KEY = "db.url";
    private static final String USER_NAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final int POOL_SIZE = 10;

    private Connection connection;
    private static BlockingQueue<Connection> connectionPool;
    private static List<Connection> usedConnections = new ArrayList<>();

    static {
        driveLoader();
    }

    private static void driveLoader() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException in connection pool");
            throw new RuntimeException(e);
        }
    }

    public static BasicConnectionPool connectPool() {
        BlockingQueue<Connection> pool = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        logger.info("Connection pool created");
        return new BasicConnectionPool(pool);
    }

    public BasicConnectionPool(BlockingQueue<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public Connection getConnection() {
        try {
            connection = connectionPool.take();
            releaseConnection(connection);
        } catch (InterruptedException e) {
            logger.error("InterruptedException in method getConnection()");
            throw new RuntimeException(e);
        }
        usedConnections.add(connection);
        logger.info("Get connection");
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        logger.info("Return connection");
        return usedConnections.remove(connection);
    }

    private static Connection createConnection() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.getKey(URL_KEY),
                    PropertiesUtil.getKey(USER_NAME_KEY),
                    PropertiesUtil.getKey(PASSWORD_KEY));
        } catch (SQLException e) {
            logger.error("SQLException in create connection method");
            throw new RuntimeException(e);
        }
    }

    public static void closePool() {
        try {
            for (Connection connection : usedConnections) {
                logger.info("Connection close");
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("SQLException when pool was closing");
            throw new RuntimeException(e);
        }
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    @Override
    public void close() {
        releaseConnection(connection);
    }
}