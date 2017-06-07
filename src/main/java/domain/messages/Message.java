package domain.messages;

import dtos.CameraServiceMessageDTO;
import dtos.IncomingMessageDTO;
import dtos.LicensePlateServiceMessageDTO;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Entity representing a camera message
 */
public class Message {
    private int cameraId;
    private LicensePlate licensePlate;
    private LocalTime timestamp;
    private Location location;
    private Segment segment;
    private int euroNormAllowed = -1;
    private String nationalNumber;
    private int euroNormVehicle = -1;

    public Message(){ }

    public Message(IncomingMessageDTO incomingMessageDTO){
        this.cameraId = incomingMessageDTO.getCameraId();
        this.timestamp = incomingMessageDTO.getTimestamp();
        this.licensePlate = incomingMessageDTO.getLicensePlate();
    }

    public Message(CameraServiceMessageDTO cameraServiceMessageDTO){
        this.cameraId = cameraServiceMessageDTO.getCameraId();
        this.location = cameraServiceMessageDTO.getLocation();
        setSegment(cameraServiceMessageDTO.getSegment());
        setEuroNormAllowed(cameraServiceMessageDTO.getEuroNormAllowed());
    }

    public int getCameraId()                { return cameraId; }
    public LicensePlate getLicensePlate()   { return licensePlate; }
    public LocalTime getTimestamp()         { return timestamp; }
    public Location getLocation()           { return location; }
    public Segment getSegment()             { return segment; }
    public int getEuroNormAllowed()         { return euroNormAllowed; }
    public String getNationalNumber()       { return nationalNumber; }
    public int getEuroNormVehicle()         { return euroNormVehicle; }


    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public void setLicensePlate(LicensePlate licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSegment(Segment segment) {
        if (segment != null)
            this.segment = segment;
    }

    public void setEuroNormAllowed(int euroNormAllowed) {
        if(euroNormAllowed != -1)
            this.euroNormAllowed = euroNormAllowed;
    }

    public void setNationalNumber(String nationalNumber) {
        if (nationalNumber != null)
            this.nationalNumber = nationalNumber;
    }

    public void setEuroNormVehicle(int euroNormVehicle) {
        if(euroNormVehicle != -1)
            this.euroNormVehicle = euroNormVehicle;
    }

    public void addInfo(CameraServiceMessageDTO cameraServiceMessageDTO){
        if (this.cameraId == cameraServiceMessageDTO.getCameraId()){
            setLocation(cameraServiceMessageDTO.getLocation());
            setSegment(cameraServiceMessageDTO.getSegment());
            setEuroNormAllowed(cameraServiceMessageDTO.getEuroNormAllowed());
        }
    }

    public void addInfo(LicensePlateServiceMessageDTO licensePlateServiceMessageDTO){
        if (licensePlate.getPlate().equals(licensePlateServiceMessageDTO.getPlateId())){
            setNationalNumber(licensePlateServiceMessageDTO.getNationalNumber());
            setEuroNormVehicle(licensePlateServiceMessageDTO.getEuroNumber());
        }
    }

    @Override
    public String toString() {
        return String.format("CameraId: %d at location:(%d;%d) monitored car with licenseplate '%s'. " +
                "Passed camera at '%s' h %s %s %s %s", cameraId, location.getLat(), location.getLongitude(), licensePlate.getPlate(),
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(timestamp),
                segment == null ? " " : "\tConnected camera: " + segment.getConnectedCameraId()
                        + " (distance: " + segment.getDistance() + " ; speedlimit: " + segment.getSpeedlimit() + ")",
                euroNormAllowed == -1 ? " " : ". EuroNorm allowed: " + euroNormAllowed,
                euroNormVehicle == -1 ? " " : " euronorm of the vehicle was : " + euroNormVehicle,
                nationalNumber == null? " " : ". National number of the car owner: " + nationalNumber);
    }
}
