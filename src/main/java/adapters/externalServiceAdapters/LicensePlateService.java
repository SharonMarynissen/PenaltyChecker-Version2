package adapters.externalServiceAdapters;

import be.kdg.se3.services.LicensePlateServiceProxy;
import com.sun.istack.internal.logging.Logger;
import domain.CommunicationException;
import domain.services.LicensePlateInformationService;
import domain.services.MessageFormatter;
import dtos.IncommingMessageDTO;
import dtos.LicensePlateServiceMessageDTO;
import java.io.IOException;

/**
 * Adapts an external CameraService to a {@link LicensePlateInformationService}
 */
public class LicensePlateService implements LicensePlateInformationService {
    private LicensePlateServiceProxy licensePlateService = new LicensePlateServiceProxy();
    private final String URL = "www.services4se3.com/license-plate/";
    private Logger logger = Logger.getLogger(LicensePlateService.class);
    private MessageFormatter formatter;

    public LicensePlateService(MessageFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LicensePlateServiceMessageDTO getInfo(IncommingMessageDTO messageDTO) throws CommunicationException {
        String messageReturned;
        LicensePlateServiceMessageDTO licensePlateServiceMessageDTO;

        try {
            messageReturned = licensePlateService.get(URL + messageDTO.getLicensePlate().getPlate());
            logger.info("Answer received from LicensePlateServiceProxy: " + messageReturned);
            licensePlateServiceMessageDTO = (LicensePlateServiceMessageDTO) formatter.format(messageReturned);
        } catch (IOException e) {
            throw new CommunicationException("Error during call to the LicensePlateService", e);
        }

        return licensePlateServiceMessageDTO;
    }
}
