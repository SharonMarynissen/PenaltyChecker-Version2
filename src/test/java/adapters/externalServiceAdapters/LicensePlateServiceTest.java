package adapters.externalServiceAdapters;

import adapters.formatters.JSONLicensePlateMessageFormatter;
import domain.messages.LicensePlate;
import dtos.IncommingMessageDTO;
import dtos.LicensePlateServiceMessageDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * Created by Sharon on 6/06/2017.
 */
public class LicensePlateServiceTest {
    IncommingMessageDTO messageDTO1;
    IncommingMessageDTO messageDTO2;
    LicensePlateService licensePlateService;

    @Before
    public void init(){
        licensePlateService = new LicensePlateService(new JSONLicensePlateMessageFormatter());
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
        LicensePlateServiceMessageDTO dto1 = licensePlateService.getInfo(messageDTO1);
        LicensePlateServiceMessageDTO dto2 = licensePlateService.getInfo(messageDTO2);

        assertNotNull("PlateId should not be null", dto1.getPlateId());
        assertNotNull("PlateId should not be null", dto2.getPlateId());
        assertNotNull("National number should not be null", dto1.getNationalNumber());
        assertNotNull("National number should not be null", dto2.getNationalNumber());
        assertNotEquals("Euronorm should not be -1", -1, dto1.getEuroNumber());
        assertNotEquals("Euronorm should not be -1", -1, dto2.getEuroNumber());
    }

}