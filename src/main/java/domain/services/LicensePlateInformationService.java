package domain.services;

import domain.CommunicationException;
import dtos.IncommingMessageDTO;
import dtos.LicensePlateServiceMessageDTO;

/**
 * Abstraction of a service calling an external license plate service
 */
public interface LicensePlateInformationService {
    /**
     * Method to get info from a license plate  service
     * @param incommingMessageDTO received from an Inputservice
     * @return LicensePlateServiceMessageDTO with information received from the service
     * @throws CommunicationException when something went wrong calling the service
     */
    LicensePlateServiceMessageDTO getInfo(IncommingMessageDTO incommingMessageDTO) throws CommunicationException;
}
