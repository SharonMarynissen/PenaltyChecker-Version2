package domain.violations;

import domain.messages.LicensePlate;
import domain.messages.Location;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


/**
 * Abstract class representing a violation.
 * A violation cant't be an instance of this class but will always be an instance of a superclass.
 * Specific violations add different information but always have the identical information of a Violation object.
 */
public abstract class Violation {
    private String type;
    private LocalTime timestamp;
    private LicensePlate licensePlate;
    private String nationalNumber;
    private Location location;

    public Violation(){

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setLicensePlate(LicensePlate licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public LocalTime getTimestamp() {
        return timestamp;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) has a %s at %s hour at location: (%d :%d)",
                nationalNumber == null ? " " : nationalNumber, licensePlate.getPlate(), type,
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(timestamp),
                location.getLat(), location.getLongitude());
    }
}
