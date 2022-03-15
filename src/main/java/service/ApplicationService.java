package service;

import dao.ApplicationDAOimpl;
import dto.ApplicationDTO;
import dto.CreateApplicationDTO;
import entity.Application;
import exeption.DaoException;
import exeption.ServiceException;
import exeption.ValidationException;
import lombok.NoArgsConstructor;
import mapper.ApplicationMapper;
import mapper.CreateApplicationMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.CreateApplicationValidation;
import validator.Validator;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor
public class ApplicationService {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

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

    public Optional<ApplicationDTO> findById(String id) {
        return applicationDAO.findById(id).map(applicationMapper::getFrom);
    }

    public Integer create(CreateApplicationDTO createApplicationDTO) {
        Integer id = 0;
        Validator validator = createApplicationValidation.resultOfValidation(createApplicationDTO);
        if (!validator.resultOfValidation()) {
            LOGGER.error("Validation error");
            throw new ValidationException(validator.getErrors());
        }
        Application application = createApplicationMapper.mapFrom(createApplicationDTO);
        try {
            id = applicationDAO.save(application);
            LOGGER.info("Application saved");
            return id;
        } catch (DaoException e) {
            LOGGER.error("Application save error");
            throw new ServiceException(e);
        }
    }

    public void delete(Integer id) {
        applicationDAO.delete(id);
    }
}

