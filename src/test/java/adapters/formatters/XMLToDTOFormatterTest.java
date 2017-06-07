package adapters.formatters;

import dtos.IncommingMessageDTO;
import domain.messages.LicensePlate;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * Created by Sharon on 6/06/2017.
 */
public class XMLToDTOFormatterTest {
    XMLToDTOFormatter formatter = new XMLToDTOFormatter();
    IncommingMessageDTO messageDTO1 = new IncommingMessageDTO();
    IncommingMessageDTO messageDTO2 = new IncommingMessageDTO();
    String messageString1;
    String messageString2;

    @Before
    public void init() {
        messageDTO1.setCameraId(1);
        messageDTO1.setTimestamp(LocalTime.of(11, 42, 13));
        messageDTO1.setLicensePlate(new LicensePlate("1-AAA-111"));

        messageDTO2.setCameraId(2);
        messageDTO2.setTimestamp(LocalTime.of(11, 45, 13));
        messageDTO2.setLicensePlate(new LicensePlate("1-BBB-111"));

        messageString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Message><camera-id>1</camera-id><timestamp>11:42:13</timestamp>" +
                "<license-plate>1-AAA-111</license-plate></Message>";

        messageString2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Message><camera-id>2</camera-id><timestamp>11:45:13</timestamp>" +
                "<license-plate>1-BBB-111</license-plate></Message>";
    }

    @Test
    public void format() throws Exception {
        IncommingMessageDTO transformed1 = formatter.format(messageString1);
        IncommingMessageDTO transformed2 = formatter.format(messageString2);

        assertNotNull("Should not be null", transformed1);
        assertNotNull("Should not be null", transformed2);

        assertEquals("String 1 formatted should equal messageDTO1",transformed1.toString(), messageDTO1.toString());
        assertEquals("String 2 formatted should equal messageDTO2",transformed2.toString(), messageDTO2.toString());
    }

}