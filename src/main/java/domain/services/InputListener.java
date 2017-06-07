package domain.services;

import dtos.IncommingMessageDTO;


/**
 * Call back interface for incoming messages
 */
public interface InputListener {
    /**
     * Methode called every time a message is received
     * @param messageDTO
     */
    void onReceive(IncommingMessageDTO messageDTO);
}
