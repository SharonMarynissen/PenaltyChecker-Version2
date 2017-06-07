package adapters.externalServiceAdapters;

import be.kdg.se3.services.LicensePlateServiceProxy;
import com.sun.istack.internal.logging.Logger;
import domain.CommunicationException;
import domain.services.LicensePlateInformationService;
import domain.services.MessageFormatter;
import dtos.IncommingMessageDTO;
import dtos.LicensePlateServiceMessageDTO;

import java.io.IOException;
import java.time.LocalTime;


/**
 * Adapts an external CameraService to a {@link LicensePlateInformationService}
 */
public class LicensePlateService implements LicensePlateInformationService {
    private LicensePlateServiceProxy licensePlateService = new LicensePlateServiceProxy();
    private final String URL = "www.services4se3.com/license-plate/";
    private Logger logger = Logger.getLogger(LicensePlateService.class);
    private MessageFormatter formatter;
    private int timesToReCheck = 5;
    private int timer = 100;
    private int i = timesToReCheck;

    public LicensePlateService(MessageFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public LicensePlateServiceMessageDTO getInfo(IncommingMessageDTO messageDTO) throws CommunicationException {
        String messageReturned;
        LicensePlateServiceMessageDTO licensePlateServiceMessageDTO = null;

        try {
            messageReturned = licensePlateService.get(URL + messageDTO.getLicensePlate().getPlate());
            logger.info("Answer received from LicensePlateServiceProxy: " + messageReturned);
            licensePlateServiceMessageDTO = (LicensePlateServiceMessageDTO) formatter.format(messageReturned);
        } catch (IOException e) {
            if (i > 0){
                logger.info("Calling the service again, times to go: " + i);
                i--;

                try {
                    Thread.sleep(timer);
                } catch (InterruptedException e1) {
                    throw new CommunicationException("Error during the waiting time to do a recall to license plate service", e);
                }

                getInfo(messageDTO);
            }

            i = timesToReCheck;
            throw new CommunicationException("Error during call to the license plate service", e);
        }

        return licensePlateServiceMessageDTO;
    }

    public int getTimer()           { return timer; }
    public int getTimesToReCheck()  { return timesToReCheck; }

    @Override
    public void setTimer(int timer) { this.timer = timer; }

    @Override
    public void setTimesToReCheck(int timesToReCheck) { this.timesToReCheck = timesToReCheck; }
}
