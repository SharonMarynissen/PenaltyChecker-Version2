package dtos;

import domain.messages.LicensePlate;
import domain.messages.Location;
import domain.violations.EuroNormViolation;
import domain.violations.SpeedViolation;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Final Data Transfer Object representing a violation used to send to an output service
 */
public final class ViolationDTO {
    private String type;
    private LocalTime timestamp;
    private LicensePlate licensePlate;
    private String nationalNumber;
    private Location location;
    private int speed = -1;
    private int maximumSpeed = -1;
    private int euroNormVehicle = -1;
    private int euroNormAllowed = -1;

    public ViolationDTO(SpeedViolation speedViolation){
        this.type = speedViolation.getType();
        this.timestamp = speedViolation.getTimestamp();
        this.licensePlate = speedViolation.getLicensePlate();
        this.nationalNumber = speedViolation.getNationalNumber();
        this.location = speedViolation.getLocation();
        this.speed = speedViolation.getSpeed();
        this.maximumSpeed = speedViolation.getMaximumSpeed();
    }

    public ViolationDTO(EuroNormViolation euroNormViolation){
        this.type = euroNormViolation.getType();
        this.timestamp = euroNormViolation.getTimestamp();
        this.licensePlate = euroNormViolation.getLicensePlate();
        this.nationalNumber = euroNormViolation.getNationalNumber();
        this.location = euroNormViolation.getLocation();
        this.euroNormVehicle = euroNormViolation.getEuroNormVehicle();
        this.euroNormAllowed = euroNormViolation.getEuroNormAllowed();
    }

    public String getType()                 { return type; }
    public LicensePlate getLicensePlate()   { return licensePlate; }
    public LocalTime getTimestamp()         { return timestamp; }
    public Location getLocation()           { return location; }
    public int getEuroNormAllowed()         { return euroNormAllowed; }
    public String getNationalNumber()       { return nationalNumber; }
    public int getEuroNormVehicle()         { return euroNormVehicle; }
    public int getSpeed()                   { return speed; }
    public int getMaximumSpeed()            { return maximumSpeed; }

    @Override
    public String toString() {
        return String.format("%s (%s) got a %s at %s hour at location: (%d :%d) %s %s",
                nationalNumber, licensePlate.getPlate(), type,
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(timestamp),
                location.getLat(), location.getLongitude(),
                speed == -1 ? " " : "\nMeasured speed: " + speed + " km/h, allowed speed " + maximumSpeed + " km/h",
                euroNormVehicle == -1 ? " " : "\nEuronorm allowed: " + euroNormAllowed + ", euronorm vehicle: "
                        + euroNormVehicle);
    }
}
