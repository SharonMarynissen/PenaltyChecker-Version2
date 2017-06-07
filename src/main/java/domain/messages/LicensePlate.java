package domain.messages;

import org.apache.log4j.Logger;

/**
 * Entity representing a license plate in the format: "1number-3letters-3numbers"
 */
public class LicensePlate {
    private String plate;
    private static final String PATTERN = "\\d-[A-Z]{3}-\\d{3}";
    private Logger logger = Logger.getLogger(LicensePlate.class);

    public LicensePlate(String plate) {
        setPlate(plate);
    }

    public void setPlate(String plate) {
        if (plate.matches(PATTERN)) {
            this.plate = plate;
            logger.info("Setting license plate: '" + plate + "' matched the required string format");
        } else
            logger.warn("License plate '" + plate + "' doesn't have the correct format: '1number-3letters-3numbers'");
    }

    public String getPlate() {
        return plate;
    }

    @Override
    public String toString() {
        return getPlate();
    }
}
