package domain.services;

import domain.CommunicationException;
import dtos.ViolationDTO;

/**
 * Abstraction of a service for sending a {@link ViolationDTO}
 */
public interface OutputService {
    /**
     * Method to send the violation to the output service
     * @param violationDTO needed to be send
     * @throws CommunicationException when something went wrong during sending sending
     */
    void sendMessage(ViolationDTO violationDTO) throws CommunicationException;

    /**
     * Closing the connection with the output service
     * @throws CommunicationException when something went wrong during closing the connection with the output service
     */
    void shutdown() throws CommunicationException;
}
