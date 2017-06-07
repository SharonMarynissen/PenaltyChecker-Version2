package adapters.queues;

import adapters.formatters.DTOToStringFormatter;
import domain.messages.LicensePlate;
import domain.messages.Location;
import domain.violations.EuroNormViolation;
import domain.violations.SpeedViolation;
import dtos.ViolationDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

/**
 * Created by Sharon on 6/06/2017.
 */
public class ViolationQueueTest {
    final String uriOutput = "amqp://ssgtyban:xuDLGGEDGZ5Toci-gDcUMnbG7IG2M294@puma.rmq.cloudamqp.com/ssgtyban";
    ViolationQueue violationQueue;
    ViolationDTO speedViolationDto;
    ViolationDTO euroNormViolationDto;

    @Before
    public void init(){
        violationQueue = new ViolationQueue(uriOutput, "VIOLATION QUEUE", new DTOToStringFormatter());
        SpeedViolation speedViolation = new SpeedViolation();
        speedViolation.setTimestamp(LocalTime.of(15,15,15));
        speedViolation.setLicensePlate(new LicensePlate("1-AAA-111"));
        speedViolation.setNationalNumber("77.01.09-182.17");
        Location location1 = new Location();
        location1.setLat(18);
        location1.setLongitude(-25);
        speedViolation.setLocation(location1);
        speedViolation.setSpeed(93);
        speedViolation.setMaximumSpeed(70);

        EuroNormViolation euroNormViolation = new EuroNormViolation();
        euroNormViolation.setTimestamp(LocalTime.of(16,16,16));
        euroNormViolation.setLicensePlate(new LicensePlate("1-BBB-111"));
        euroNormViolation.setNationalNumber("99.02.12-182.03");
        Location location2 = new Location();
        location2.setLat(77);
        location2.setLongitude(-87);
        euroNormViolation.setLocation(location2);
        euroNormViolation.setEuroNormAllowed(4);
        euroNormViolation.setEuroNormVehicle(2);

        speedViolationDto = new ViolationDTO(speedViolation);
        euroNormViolationDto = new ViolationDTO(euroNormViolation);
    }

    @Test
    public void sendMessage() throws Exception {
        violationQueue.sendMessage(speedViolationDto);
        violationQueue.sendMessage(euroNormViolationDto);
    }

    @Test
    public void shutdown() throws Exception {
        violationQueue.sendMessage(speedViolationDto);
        violationQueue.shutdown();
    }

}