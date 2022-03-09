package dao;

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
import java.util.List;

public class ApplicationDAOimplTest {
    ApplicationDAOimpl applicationDAOimpl = new ApplicationDAOimpl();
    private static final String SEARCH_APPLICATION = "SELECT * FROM application WHERE bed = 'ONE' AND email = 'email' AND time = 1 AND type = 'ECONOMY' AND processing_status = 0 AND status = false;";

    @Test
    public void save() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_APPLICATION)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Application application = getApplication();
            if (resultSet.next()) {
                Application application2 = applicationDAOimpl.createApplication(resultSet);
                application.setId(application2.getId());
                Assert.assertEquals(application, application2);
            }
        }
    }

    @Test
    public void getPendingApplication() {
        for (int i = 0; i < 3; i++) {
            getApplication();
        }
        List<Application> list2 = applicationDAOimpl.getPendingApplication();
        for (Application application : list2) {
            Assert.assertEquals(0, application.getProcessing_status());
        }
    }

    @Test
    public void processApplication() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_APPLICATION)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            getApplication();
            if (resultSet.next()) {
                Application application2 = applicationDAOimpl.createApplication(resultSet);
                Integer id = application2.getId();
                applicationDAOimpl.processApplication(id);
                application2 = applicationDAOimpl.findById(id.toString()).get();
                Assert.assertEquals(1, application2.getProcessing_status());
            }
        }
    }

    @Test
    public void statusApplication() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_APPLICATION)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            getApplication();
            if (resultSet.next()) {
                Application application2 = applicationDAOimpl.createApplication(resultSet);
                Integer id = application2.getId();
                applicationDAOimpl.statusApplication(id, "true");
                application2 = applicationDAOimpl.findById(id.toString()).get();
                applicationDAOimpl.delete(id);
                Assert.assertTrue(application2.getStatus());
            }
        }
    }

    @Test
    public void delete() throws SQLException {
        try (Connection connection = BasicConnectionPool.connectPool().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_APPLICATION)) {
            Application application = getApplication();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                application = applicationDAOimpl.createApplication(resultSet);
                Integer id = application.getId();
                applicationDAOimpl.delete(id);
                Assert.assertFalse(applicationDAOimpl.findById(id.toString()).isPresent());
            }
        }
    }

    private Application getApplication() {
        Application application = Application.builder()
                .bed(Bed.ONE)
                .email("email")
                .time(1)
                .type(Type.ECONOMY)
                .processing_status(0)
                .status(false).build();
        applicationDAOimpl.save(application);
        return application;
    }
}