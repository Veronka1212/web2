package service;

import dto.ApplicationDTO;
import dto.CreateApplicationDTO;
import entity.Application;
import entity.room.Bed;
import entity.room.Type;
import mapper.ApplicationMapper;
import mapper.CreateApplicationMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class ApplicationServiceTest {
    final ApplicationService applicationService = new ApplicationService();
    final CreateApplicationDTO applicationDTO = CreateApplicationDTO.builder()
            .bed("ONE")
            .email("test@mail.com")
            .time("1")
            .type("LUX")
            .status("false")
            .processing_status("0")
            .build();

    @Test
    public void findAllPending() {

    }

    @Test
    public void findByEmail() {
        Integer id = applicationService.create(applicationDTO);
        ApplicationDTO appDTO2 = applicationService.findByEmail(applicationDTO.getEmail()).get(0);
        applicationService.delete(id);
        Assert.assertEquals(returnApplicationDTO(id), appDTO2);
    }

    @Test
    public void findById() {
        Integer id = applicationService.create(applicationDTO);
        ApplicationDTO appDTO2 = applicationService.findById(id.toString()).get();
        applicationService.delete(id);
        Assert.assertEquals(returnApplicationDTO(id), appDTO2);
    }

    @Test
    public void create() {
        Integer id = applicationService.create(applicationDTO);
        Assert.assertEquals(returnApplicationDTO(id), applicationService.findById(id.toString()));
        applicationService.delete(id);
    }

    @Test
    public void delete() {
        Integer id = applicationService.create(applicationDTO);
        applicationService.delete(id);
        Assert.assertEquals(Optional.empty(), applicationService.findById(id.toString()));
    }

    @Test
    public void adminProcessApplication() {
        Integer id = applicationService.create(applicationDTO);
        applicationService.adminProcessApplication(id, "true");
        ApplicationDTO applicationDTO = applicationService.findById(id.toString()).get();
        applicationService.delete(id);
        Assert.assertTrue(applicationDTO.isStatus());
    }

    private ApplicationDTO returnApplicationDTO(Integer id) {
        return ApplicationDTO.builder()
                .id(id)
                .bed(Bed.ONE)
                .email("test@mail.com")
                .time(1)
                .type(Type.LUX)
                .status(Boolean.FALSE)
                .processing_status(0)
                .build();
    }
}