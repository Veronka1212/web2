package service;

import dao.ApplicationDAOimpl;
import dto.ApplicationDTO;
import dto.CreateApplicationDTO;
import entity.Application;
import exeption.DaoException;
import exeption.ValidationException;
import lombok.NoArgsConstructor;
import mapper.ApplicationMapper;
import mapper.CreateApplicationMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.CreateApplicationValidation;
import validator.Validator;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
public class ApplicationService {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    private final ApplicationDAOimpl applicationDAO = new ApplicationDAOimpl();
    private final CreateApplicationMapper createApplicationMapper = new CreateApplicationMapper();
    private final ApplicationMapper applicationMapper = new ApplicationMapper();
    private final CreateApplicationValidation createApplicationValidation = new CreateApplicationValidation();
    public List<ApplicationDTO> findAllPending() {
        return applicationDAO.getPendingApplication().stream()
                .map(applicationMapper::getFrom)
                .collect(toList());
    }

    public void adminProcessApplication(Integer id, String status) {
        applicationDAO.statusApplication(id, status);
        applicationDAO.processApplication(id);
    }

    public List<ApplicationDTO> findByEmail(String email) {
        return applicationDAO.findByEmail(email).stream()
                .map(applicationMapper::getFrom)
                .collect(toList());
    }

    public ApplicationDTO findById(String id){
        return applicationDAO.findById(id)
                .map(applicationMapper::getFrom)
                .get();
    }

    public Integer create(CreateApplicationDTO createApplicationDTO){
        Validator validator = createApplicationValidation.resultOfValidation(createApplicationDTO);
        if (!validator.resultOfValidation()) {
            logger.error("Validation error");
            throw new ValidationException(validator.getErrors());
        }
        Application application = createApplicationMapper.mapFrom(createApplicationDTO);
        try {
            applicationDAO.save(application);
            logger.info("Application saved");
            return application.getId();
        } catch (DaoException e) {
            logger.error("Application save error");
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer id){
        applicationDAO.delete(id);
    }
}

