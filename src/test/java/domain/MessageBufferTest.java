package domain;

import domain.messages.LicensePlate;
import domain.messages.Location;
import domain.messages.Message;
import domain.messages.Segment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

/**
 * Created by Sharon on 6/06/2017.
 */
public class MessageBufferTest {
    MessageBuffer buffer = new MessageBuffer();
    Message message1;
    Message message2;
    Message message3;

    @Before
    public void init(){
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
        message2.setLicensePlate(new LicensePlate("1-BBB-111"));
        message2.setTimestamp(LocalTime.of(11,45,13));
        Location location2 = new Location();
        location2.setLat(-7);
        location2.setLongitude(166);
        message2.setLocation(location2);

        message3 = new Message();
        message3.setCameraId(2);
        message3.setLicensePlate(new LicensePlate("1-AAA-111"));
        message3.setTimestamp(LocalTime.of(11,45,15));
        Location location3 = new Location();
        location3.setLat(-25);
        location3.setLongitude(105);
        message3.setLocation(location3);
    }

    @Test
    public void checkMessage() throws Exception {
        buffer.checkMessage(message1);
        buffer.checkMessage(message2);
        buffer.checkMessage(message3);
    }

    @Test
    public void setTimer() throws Exception {
        buffer.setTimer(120);
        buffer.checkMessage(message1);
    }

}