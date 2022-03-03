package mapper;

import dto.CreateApplicationDTO;
import entity.Application;
import entity.room.Bed;
import entity.room.Type;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateApplicationMapper implements Mapperalable<CreateApplicationDTO, Application> {

    @Override
    public Application mapFrom(CreateApplicationDTO object) {
        return Application.builder().
                email(object.getEmail()).
                bed(Bed.valueOf(object.getBed())).
                type(Type.valueOf(object.getType())).
                time(Integer.parseInt(object.getTime())).
                status(Boolean.getBoolean(object.getStatus())).
                processing_status(Integer.parseInt(object.getProcessing_status())).
                build();
    }
}
