package domain.services;

import domain.CommunicationException;
import dtos.CameraServiceMessageDTO;
import dtos.IncomingMessageDTO;

/**
 * Abstraction of a service calling an external camera service
 */
public interface CameraInformationService {
    /**
     * Method to get info from a camera service
     * @param incomingMessageDTO received from an Inputservice
     * @return CameraServiceMessageDTO with information received from the service
     * @throws CommunicationException when something went wrong calling the service
     */
    CameraServiceMessageDTO getInfo(IncomingMessageDTO incomingMessageDTO) throws CommunicationException;
}
