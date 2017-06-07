package domain.services;

import domain.CommunicationException;

/**
 * Abstraction for the conversion of messages
 */
public interface MessageFormatter<I, O> {
    /**
     *
     * @param input of the type I
     * @return output of type O
     * @throws CommunicationException when something went wrong during formatting the input into the output
     */
    O format(I input) throws CommunicationException;
}
