package domain.services;

import domain.CommunicationException;
import dtos.IncomingMessageDTO;
import dtos.LicensePlateServiceMessageDTO;

/**
 * Abstraction of a service calling an external license plate service
 */
public interface LicensePlateInformationService {
    /**
     * Method to get info from a license plate  service
     * @param incomingMessageDTO received from an Inputservice
     * @return LicensePlateServiceMessageDTO with information received from the service
     * @throws CommunicationException when something went wrong calling the service
     */
    LicensePlateServiceMessageDTO getInfo(IncomingMessageDTO incomingMessageDTO) throws CommunicationException;

    /**
     * Set timer to redo a call to a license plate service in milliseconds
     * @param timer in milliseconds
     */
    void setTimer(int timer);

    /**
     * Set number of times you want to try to do a recall to a license plate service
     * @param timesToReCheck number of times you want to retry to do a call to the license plate service
     */
    void setTimesToReCheck(int timesToReCheck);
}
