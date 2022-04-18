package mapper;

import dto.ApplicationDTO;
import entity.Application;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApplicationMapper {

    public ApplicationDTO getFrom(Application application) {
        return ApplicationDTO.builder()
                .id(application.getId())
                .email(application.getEmail())
                .bed(application.getBed())
                .type(application.getType())
                .time(application.getTime())
                .status(application.getStatus())
                .processing_status(application.getProcessing_status())
                .build();
    }
}
