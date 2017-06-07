package dtos;

import domain.messages.LicensePlate;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Data Transfer Object for converting an incoming message format to a message
 */
public class IncomingMessageDTO {
    private int cameraId;
    private LicensePlate licensePlate;
    private LocalTime timestamp;

    public IncomingMessageDTO() {
    }

    public void setCameraId(int cameraId)                        { this.cameraId = cameraId; }
    public void setLicensePlate(LicensePlate licensePlate)       { this.licensePlate = licensePlate; }
    public void setTimestamp(LocalTime timestamp)                { this.timestamp = timestamp; }


    public int getCameraId()                      { return cameraId; }
    public LicensePlate getLicensePlate()         { return licensePlate; }
    public LocalTime getTimestamp()               { return timestamp; }

    @Override
    public String toString() {
        return String.format("Camera %-5d %-12s %s", cameraId, licensePlate,
                DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(timestamp));
    }

}
