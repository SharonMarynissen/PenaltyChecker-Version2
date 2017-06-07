package domain.checkers;

import domain.messages.Message;
import domain.violations.SpeedViolation;
import domain.violations.Violation;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Checker of the speed of a vehicle
 */
public class SpeedLimitChecker {
    private Logger logger = Logger.getLogger(SpeedLimitChecker.class);
    SpeedViolation speedViolation;

    public SpeedViolation checkForViolation(Message messageSegment1,Message messageSegment2){
        logger.info("Checking for speedviolations...");
        speedViolation = null;
        LocalTime time1 = messageSegment1.getTimestamp();
        LocalTime time2 = messageSegment2.getTimestamp();

        long timeBetweenInSeconds = Duration.between(time1, time2).getSeconds();
        logger.info("Time in seconds between to messages: " + timeBetweenInSeconds);

        int speed = calculateSpeed(messageSegment1.getSegment().getDistance(), timeBetweenInSeconds);
        logger.info("Measured speed is: " + speed + " km/h");

        if (speed > messageSegment1.getSegment().getSpeedlimit()){
            logger.info("Speed violation was made");
            speedViolation = new SpeedViolation();
            speedViolation.setSpeed(speed);
            speedViolation.setMaximumSpeed(messageSegment1.getSegment().getSpeedlimit());
            addInfoToViolation(messageSegment1, messageSegment2);
            logger.info("Returning speed violation: " + speedViolation);
        }
        else
            logger.info("No speed violation was made");

        return speedViolation;
    }

    private int calculateSpeed(int distance, long timeInSeconds){
        return (int) (distance / timeInSeconds * 3.6);
    }

    private void addInfoToViolation(Message messageSegment1, Message messageSegment2){
        logger.info("Adding info to speedViolation");
        speedViolation.setTimestamp(messageSegment2.getTimestamp());
        speedViolation.setLicensePlate(messageSegment1.getLicensePlate());
        speedViolation.setLocation(messageSegment2.getLocation());
    }
}
