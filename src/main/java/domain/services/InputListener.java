package domain.services;

import dtos.IncomingMessageDTO;


/**
 * Call back interface for incoming messages
 */
public interface InputListener {
    /**
     * Methode called every time a message is received
     * @param messageDTO the message received from input service
     */
    void onReceive(IncomingMessageDTO messageDTO);
}
