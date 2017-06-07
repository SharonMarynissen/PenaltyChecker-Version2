package domain.violations;

import domain.messages.Message;

/**
 * Is a {@link Violation} but adds extra information about the euronorm
 */
public class EuroNormViolation extends Violation {
    private int euroNormVehicle;
    private int euroNormAllowed;

    public EuroNormViolation(){
        super.setType("Euro norm violation");
    }

    public EuroNormViolation(Message message){
        super.setType("Euro norm violation");
        super.setLicensePlate(message.getLicensePlate());
        super.setLocation(message.getLocation());
        super.setTimestamp(message.getTimestamp());
        super.setNationalNumber(message.getNationalNumber());
        this.euroNormVehicle = message.getEuroNormVehicle();
        this.euroNormAllowed = message.getEuroNormAllowed();
    }

    public int getEuroNormAllowed() {
        return euroNormAllowed;
    }

    public void setEuroNormAllowed(int euroNormAllowed) {
        this.euroNormAllowed = euroNormAllowed;
    }

    public int getEuroNormVehicle() {
        return euroNormVehicle;
    }

    public void setEuroNormVehicle(int euroNormVehicle) {
        this.euroNormVehicle = euroNormVehicle;
    }

    @Override
    public String toString() {
        return String.format("%s\nEuronorm allowed: %d, euronorm vehicle: %d",
                super.toString(), euroNormAllowed, euroNormVehicle);
    }
}
