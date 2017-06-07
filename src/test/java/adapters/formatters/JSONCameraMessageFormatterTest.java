package adapters.formatters;

import dtos.CameraServiceMessageDTO;
import domain.CommunicationException;
import domain.messages.Location;
import domain.messages.Segment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class test for {@link JSONCameraMessageFormatter}
 */
public class JSONCameraMessageFormatterTest {
    JSONCameraMessageFormatter formatter = new JSONCameraMessageFormatter();
    String string1;
    String string2;
    String string3;
    String string4;
    String string5;
    CameraServiceMessageDTO dto1;
    CameraServiceMessageDTO dto2;
    CameraServiceMessageDTO dto3;

    @Before
    public void init(){
        string1 = "{\"cameraId\":3,\"location\":{\"lat\":-66,\"long\":-144},\"segment\":{\"connectedCameraId\":4," +
                "\"distance\":4000,\"speedLimit\":70},\"euroNorm\":2}";

        string2 = "{\"cameraId\":4,\"location\":{\"lat\":11,\"long\":-154},\"euroNorm\":3}";

        string3 = "{\"cameraId\":2,\"location\":{\"lat\":70,\"long\":-167}}";

        string4 = "{\"error\":\"UnknownCameraId\",\"description\":\"Unknown camera id (> 100)\"}";
        string5 = "{\"error\":\"InvalidCameraId\",\"description\":\"Unable to convert id to an int\"}";

        dto1 = new CameraServiceMessageDTO();
        dto1.setCameraId(3);
        Location location1 = new Location();
        location1.setLat(-66);
        location1.setLongitude(-144);
        dto1.setLocation(location1);
        Segment segment1 = new Segment();
        segment1.setConnectedCameraId(4);
        segment1.setDistance(4000);
        segment1.setSpeedlimit(70);
        dto1.setSegment(segment1);
        dto1.setEuroNormAllowed(2);

        dto2 = new CameraServiceMessageDTO();
        dto2.setCameraId(4);
        Location location2 = new Location();
        location2.setLat(11);
        location2.setLongitude(-154);
        dto2.setLocation(location2);
        dto2.setEuroNormAllowed(3);

        dto3 = new CameraServiceMessageDTO();
        dto3.setCameraId(2);
        Location location3 = new Location();
        location3.setLat(70);
        location3.setLongitude(-167);
        dto3.setLocation(location3);
    }

    @Test
    public void formatGood() throws Exception {
        CameraServiceMessageDTO transformed1 = formatter.format(string1);
        CameraServiceMessageDTO transformed2 = formatter.format(string2);
        CameraServiceMessageDTO transformed3 = formatter.format(string3);

        assertNotNull("Should not be null", transformed1);
        assertNotNull("Should not be null", transformed2);
        assertNotNull("Should not be null", transformed3);

        assertEquals("Should be equal", transformed1.toString(), dto1.toString());
        assertEquals("Should be equal", transformed2.toString(), dto2.toString());
        assertEquals("Should be equal", transformed3.toString(), dto3.toString());
    }

    @Test (expected = CommunicationException.class)
    public void formatError() throws CommunicationException {
        formatter.format(string4);
        formatter.format(string5);
        fail("Should have thrown CommunicationException");
    }
}