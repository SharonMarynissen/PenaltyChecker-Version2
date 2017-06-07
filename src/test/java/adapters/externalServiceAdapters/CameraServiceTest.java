package adapters.externalServiceAdapters;

import adapters.formatters.JSONCameraMessageFormatter;
import dtos.CameraServiceMessageDTO;
import dtos.IncommingMessageDTO;
import domain.messages.LicensePlate;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * Created by Sharon on 6/06/2017.
 */
public class CameraServiceTest {
    IncommingMessageDTO messageDTO1;
    IncommingMessageDTO messageDTO2;
    CameraService cameraService;

    @Before
    public void init(){
        cameraService = new CameraService(new JSONCameraMessageFormatter());
        messageDTO1 =  new IncommingMessageDTO();
        messageDTO1.setCameraId(1);
        messageDTO1.setTimestamp(LocalTime.of(11, 42, 13));
        messageDTO1.setLicensePlate(new LicensePlate("1-AAA-111"));

        messageDTO2 = new IncommingMessageDTO();
        messageDTO2.setCameraId(2);
        messageDTO2.setTimestamp(LocalTime.of(11, 45, 13));
        messageDTO2.setLicensePlate(new LicensePlate("1-BBB-111"));
    }


    @Test
    public void getInfo() throws Exception {
        CameraServiceMessageDTO dto1 = cameraService.getInfo(messageDTO1);
        CameraServiceMessageDTO dto2 = cameraService.getInfo(messageDTO2);

        assertNotNull("CameraId should not be null", dto1.getCameraId());
        assertNotNull("CameraId should not be null", dto2.getCameraId());
        assertNotNull("Location should not be null", dto1.getLocation());
        assertNotNull("Location should not be null", dto2.getLocation());
    }

}