package adapters.externalServiceAdapters;

import adapters.formatters.JSONLicensePlateMessageFormatter;
import domain.CommunicationException;
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
    IncommingMessageDTO messageDTO3;
    LicensePlateService licensePlateService;

    @Before
    public void init(){
        licensePlateService = new LicensePlateService(new JSONLicensePlateMessageFormatter());
        messageDTO1 = new IncommingMessageDTO();
        messageDTO1.setCameraId(1);
        messageDTO1.setTimestamp(LocalTime.of(11, 42, 13));
        messageDTO1.setLicensePlate(new LicensePlate("1-AAA-123"));


        messageDTO2 = new IncommingMessageDTO();
        messageDTO2.setCameraId(2);
        messageDTO2.setTimestamp(LocalTime.of(11, 45, 13));
        messageDTO2.setLicensePlate(new LicensePlate("1-BBB-111"));

        messageDTO3 = new IncommingMessageDTO();
        messageDTO3.setCameraId(89);
        messageDTO3.setTimestamp(LocalTime.of(12, 12, 13));
        messageDTO3.setLicensePlate(new LicensePlate("1-ERR-123"));
    }

    @Test
    public void getInfo() throws CommunicationException {
        LicensePlateServiceMessageDTO dto1 = licensePlateService.getInfo(messageDTO1);
        LicensePlateServiceMessageDTO dto2 = licensePlateService.getInfo(messageDTO2);

        assertNotNull("PlateId should not be null", dto1.getPlateId());
        assertNotNull("PlateId should not be null", dto2.getPlateId());
        assertNotNull("National number should not be null", dto1.getNationalNumber());
        assertNotNull("National number should not be null", dto2.getNationalNumber());
        assertNotEquals("Euronorm should not be -1", -1, dto1.getEuroNumber());
        assertNotEquals("Euronorm should not be -1", -1, dto2.getEuroNumber());
    }

    @Test (expected = CommunicationException.class)
    public void getInfoShouldThrowException() throws CommunicationException {
        licensePlateService.getInfo(messageDTO3);
        fail("Should throw a communication exception");
    }

}