package tests;

import dao.ApplicationDAOimpl;
import entity.Application;
import entity.room.Bed;
import entity.room.Type;
import jdbc.BasicConnectionPool;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ApplicationDAOimplTest {

    @Test
    public void save() throws SQLException {
        final String searchApplication = "SELECT * FROM application WHERE bed = 'ONE' AND email = 'email' AND time = 1 AND type = 'ECONOMY' AND processing_status = 0 AND status = false;";
        ApplicationDAOimpl applicationDAOimpl = new ApplicationDAOimpl();
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(searchApplication)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Application application = Application.builder()
                    .bed(Bed.ONE)
                    .email("email")
                    .time(1)
                    .type(Type.ECONOMY)
                    .processing_status(0)
                    .status(false).build();
            applicationDAOimpl.save(application);
            if (resultSet.next()) {
                Application application2 = applicationDAOimpl.createApplication(resultSet);
                application.setId(application2.getId());
                Assert.assertEquals(application, application2);
            }
        }
    }

    @Test
    public void getPendingApplication() {
    }

    @Test
    public void processApplication() {
    }

    @Test
    public void statusApplication() {
    }

    @Test
    public void delete() {
    }
}