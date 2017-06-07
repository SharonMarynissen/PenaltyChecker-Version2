package dtos;

/**
 * Message Data Transfer Object that contains all the information returned by a LicensePlateInformationService
 */
public class LicensePlateServiceMessageDTO {
    private String plateId;
    private String nationalNumber;
    private int euroNumber = -1;

    public String getPlateId()          { return plateId; }
    public String getNationalNumber()   { return nationalNumber; }
    public int getEuroNumber()          { return euroNumber; }

    public void setPlateId(String plateId)                  { this.plateId = plateId; }
    public void setNationalNumber(String nationalNumber)    { this.nationalNumber = nationalNumber; }
    public void setEuroNumber(int euroNumber)               { this.euroNumber = euroNumber; }

    @Override
    public String toString() {
        return String.format("Licenseplate '%s' of owner '%s' has euronorm %d", plateId, nationalNumber, euroNumber);
    }
}
