package adapters.formatters;

import dtos.LicensePlateServiceMessageDTO;
import domain.CommunicationException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sharon on 6/06/2017.
 */
public class JSONLicensePlateMessageFormatterTest {
    JSONLicensePlateMessageFormatter formatter = new JSONLicensePlateMessageFormatter();
    String string1;
    String string2;
    String string3;
    LicensePlateServiceMessageDTO dto1;
    LicensePlateServiceMessageDTO dto2;

    @Before
    public void init(){
        string1 = "{\"plateId\":\"1-AAA-123\",\"nationalNumber\":\"56.11.01-332.09\",\"euroNumber\":1}";
        string2 = "{\"plateId\":\"1-BBB-123\",\"nationalNumber\":\"99.02.12-182.03\",\"euroNumber\":3}";
        string3 = "{\"error\":\"UnknownPlateId\",\"description\":\"Forced by 1-UNK-123\"}";

        dto1 = new LicensePlateServiceMessageDTO();
        dto1.setPlateId("1-AAA-123");
        dto1.setNationalNumber("56.11.01-332.09");
        dto1.setEuroNumber(1);

        dto2 = new LicensePlateServiceMessageDTO();
        dto2.setPlateId("1-BBB-123");
        dto2.setNationalNumber("99.02.12-182.03");
        dto2.setEuroNumber(3);
    }

    @Test
    public void formatGood() throws Exception {
        LicensePlateServiceMessageDTO transformed1 = formatter.format(string1);
        LicensePlateServiceMessageDTO transformed2 = formatter.format(string2);

        assertNotNull("Should not be null", transformed1);
        assertNotNull("Should not be null", transformed2);

        assertEquals("Should be the same", transformed1.toString(), dto1.toString());
        assertEquals("Should be the same", transformed2.toString(), dto2.toString());
    }

    @Test(expected = CommunicationException.class)
    public void formatError() throws CommunicationException{
        formatter.format(string3);
        fail("Should have thrown exception");
    }

}