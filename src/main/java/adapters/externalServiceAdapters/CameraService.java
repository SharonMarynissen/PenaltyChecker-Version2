package adapters.externalServiceAdapters;

import domain.services.CameraInformationService;
import dtos.CameraServiceMessageDTO;
import dtos.IncomingMessageDTO;
import be.kdg.se3.services.CameraServiceProxy;
import com.sun.istack.internal.logging.Logger;
import domain.CommunicationException;
import domain.services.MessageFormatter;
import java.io.IOException;

/**
 * Adapts an external CameraService to a {@link CameraInformationService}
 */
public class CameraService implements CameraInformationService {
    private CameraServiceProxy cameraService = new CameraServiceProxy();
    private final String URL = "www.services4se3.com/camera/";
    private MessageFormatter formatter;
    private Logger logger = Logger.getLogger(CameraService.class);

    public CameraService(MessageFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public CameraServiceMessageDTO getInfo(IncomingMessageDTO messageDTO) throws CommunicationException {
        CameraServiceMessageDTO cameraServiceMessageDTO;

        try {
            String messageReturned = cameraService.get(URL + messageDTO.getCameraId());
            logger.info("Answer received from CameraServiceProxy: " + messageReturned);

            cameraServiceMessageDTO = (CameraServiceMessageDTO) formatter.format(messageReturned);
        } catch (IOException | CommunicationException e) {
            throw new CommunicationException("Error during calling external cameraservice", e);
        }

        return cameraServiceMessageDTO;
    }

}
