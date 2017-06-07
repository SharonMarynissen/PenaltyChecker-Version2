package domain.checkers;

import domain.messages.LicensePlate;
import domain.messages.Location;
import domain.messages.Message;
import domain.messages.Segment;
import domain.violations.SpeedViolation;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Test class for {@link SpeedLimitChecker}
 */
public class SpeedLimitCheckerTest {
    SpeedLimitChecker checker;
    Message message1;
    Message message2;
    Message message3;

    @Before
    public void init(){
        checker = new SpeedLimitChecker();
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

        message2 = new Message();
        message2.setCameraId(2);
        message2.setLicensePlate(new LicensePlate("1-AAA-111"));
        message2.setTimestamp(LocalTime.of(11,43,15));
        Location location2 = new Location();
        location2.setLat(-25);
        location2.setLongitude(105);
        message2.setLocation(location2);

        message3 = new Message();
        message3.setCameraId(2);
        message3.setLicensePlate(new LicensePlate("1-AAA-111"));
        message3.setTimestamp(LocalTime.of(12,8,15));
        Location location3 = new Location();
        location3.setLat(78);
        location3.setLongitude(15);
        message3.setLocation(location3);
    }


    @Test
    public void checkForViolation() {
        SpeedViolation violation = checker.checkForViolation(message1, message2);
        assertNotNull("Should return a violation", violation);
        assertEquals("Speedlimit should be 30", 30, violation.getMaximumSpeed());
        assertEquals("Measured speed should be 115", 115, violation.getSpeed());

        SpeedViolation noViolation = checker.checkForViolation(message1, message3);
        assertNull("Should not return a violation", noViolation);
    }
}