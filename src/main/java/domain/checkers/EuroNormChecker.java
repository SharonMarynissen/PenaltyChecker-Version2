package domain.checkers;

import domain.messages.Message;
import domain.violations.EuroNormViolation;
import domain.violations.Violation;
import org.apache.log4j.Logger;

/**
 * Checker of the euronorm of a vehicle
 */
public class EuroNormChecker {
    private Logger logger = Logger.getLogger(EuroNormChecker.class);
    EuroNormViolation euroNormViolation;

    public EuroNormViolation checkMessage(Message message){
        logger.info("Checking for euronorm violations...");
        euroNormViolation = null;

        if (message.getEuroNormVehicle() < message.getEuroNormAllowed()){
            logger.info("A violation was made; euronorm of the vehicle is lower than the allowed euronorm");
            euroNormViolation = new EuroNormViolation(message);
            return euroNormViolation;
        }

        else{
            logger.info("No violation was made; euronorm of the vehicle was higher than the allowed euronorm");
            return euroNormViolation;
        }
    }
}
