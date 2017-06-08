package domain;

import domain.messages.Message;
import org.apache.log4j.Logger;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Entity representing a message buffer used for caching
 */
public class MessageBuffer {
    private Map<Integer, List<Message>> buffer;
    private Logger logger = Logger.getLogger(MessageBuffer.class);
    private int timer = 30;
    private LocalTime now;

    public MessageBuffer(){
        buffer = new ConcurrentHashMap<>();
    }

    public Message checkMessage(Message message){
        logger.info("Checking the message buffer for a matching first segment message");

        if (buffer.containsKey(message.getCameraId())){
            logger.info("Buffer contains a first segment message");
            List<Message> messages = buffer.get(message.getCameraId());

            for (Message msg : messages){
                if (message.getLicensePlate().getPlate().equals(msg.getLicensePlate().getPlate())){
                    logger.info("The second segment license plate matches a first segment license plate");
                    logger.info("Removing message '" + msg + "' from buffer");
                    removeMessage(messages, msg);
                    logger.info("Returning message: '" + msg.toString() + "'");
                    return msg;
                }
            }

            logger.info("No matching license plate was found");
        }
        else
            logger.info("Buffer does not contain a first segment message for this camera id");

        if (message.getSegment() != null)
            addMessage(message);

        checkTime();

        return null;
    }

    private void addMessage(Message message){
        Integer id = message.getSegment().getConnectedCameraId();

        if (!buffer.containsKey(id))
            buffer.put(id, new ArrayList<>());

        buffer.get(id).add(message);
        logger.info("Message added to the buffer: KEY: " + id + ",VALUE: " + message);
    }

    private void removeMessage(Collection<Message> messageList, Message message){
        logger.info("Removing the message: " + message);
        messageList.remove(message);

        if (messageList.size() == 0) {
            int keyEntrance = message.getSegment().getConnectedCameraId();
            logger.info("Key '" + keyEntrance + "' doesn't keep any values any more");
            buffer.remove(keyEntrance);
            logger.info("Removed key entrance " + keyEntrance);
        }
    }

    private void checkTime(){
        //now = LocalTime.now();
        now = LocalTime.of(12,12,18);   //This is just for testing, in reality you will use LocalTime.now()
        logger.info("Starting to check the timestamps of the buffered messages");
        Set<Integer> keyvalues = buffer.keySet();

        for (Iterator<Integer> iterator = keyvalues.iterator(); iterator.hasNext(); ) {
            Integer next =  iterator.next();
            List<Message> messages = buffer.get(next);

            for (Iterator<Message> messageIterator = messages.iterator(); messageIterator.hasNext(); ) {
                Message message =  messageIterator.next();
                long timeBetween = Duration.between(message.getTimestamp(), now).toMinutes();

                if (timeBetween > timer){
                    messageIterator.remove();
                    logger.info("Removed message: " + message + "... Time to keep a message in buffer was passed");
                }
            }
        }

        checkForEmptyKeyValues(keyvalues);
        logger.info("Done checking the timestamp of the buffered messages");
    }

    private void checkForEmptyKeyValues(Set<Integer> values){
        logger.info("Checking for empty key values");

        for (Integer i : values) {
            List<Message> messages = buffer.get(i);

            if(messages.size() == 0){
                buffer.remove(i);
                logger.info("Removed empty key value for key: " + i);
            }
        }
        logger.info("Done checking for empty key values");
    }

    /**
     * Set the timer represented in minutes
     * @param timer in minutes
     */
    public void setTimer(int timer)     {
        logger.info("Setting timer to keep messages into buffer");
        this.timer = timer;
    }

    public int getTimer()               { return timer; }
}
