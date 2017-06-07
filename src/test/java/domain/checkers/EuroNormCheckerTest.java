package domain.checkers;

import domain.messages.LicensePlate;
import domain.messages.Location;
import domain.messages.Message;
import domain.messages.Segment;
import domain.violations.EuroNormViolation;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

/**
 * Created by Sharon on 6/06/2017.
 */
public class EuroNormCheckerTest {
    EuroNormChecker euroNormChecker;
    Message message1;
    Message message2;

    @Before
    public void init(){
        euroNormChecker = new EuroNormChecker();
        message1 = new Message();
        message1.setCameraId(1);
        message1.setLicensePlate(new LicensePlate("1-AAA-111"));
        message1.setTimestamp(LocalTime.of(11,42,13));
        Location location1 = new Location();
        location1.setLat(85);
        location1.setLongitude(26);
        message1.setLocation(location1);
        Segment segment1 = new Segment();
        segment1.setDistance(2000);
        segment1.setSpeedlimit(30);
        segment1.setConnectedCameraId(2);
        message1.setSegment(segment1);
        message1.setEuroNormAllowed(4);
        message1.setEuroNormVehicle(2);

        message2 = new Message();
        message2.setCameraId(2);
        message2.setLicensePlate(new LicensePlate("1-BBB-111"));
        message2.setTimestamp(LocalTime.of(11,45,13));
        Location location2 = new Location();
        location2.setLat(-7);
        location2.setLongitude(166);
        message2.setLocation(location2);
        message2.setEuroNormVehicle(4);
        message2.setEuroNormAllowed(2);
    }

    @Test
    public void checkMessage() throws Exception {
        EuroNormViolation out1 = euroNormChecker.checkMessage(message1);
        EuroNormViolation out2 = euroNormChecker.checkMessage(message2);

        assertNotNull("Should not be null", out1);
        assertEquals("Euronorm of the vehicle should be 2", 2, out1.getEuroNormVehicle());
        assertEquals("Euronorm allowed should be 4", 4, out1.getEuroNormAllowed());
        assertNull("Should be null", out2);
    }

}