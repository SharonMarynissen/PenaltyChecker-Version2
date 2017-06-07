package adapters.queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import domain.CommunicationException;
import domain.services.MessageFormatter;
import domain.services.OutputService;
import dtos.ViolationDTO;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Adapts an {@link OutputService} to a RabbitMQ queue
 */
public class ViolationQueue implements OutputService {
    private Logger logger = Logger.getLogger(ViolationQueue.class);
    private MessageFormatter formatter;
    private String uri;
    private String queueName;

    private Connection connection = null;
    private Channel channel = null;

    public ViolationQueue(String uri, String queueName, MessageFormatter formatter){
        this.uri = uri;
        this.queueName = queueName;
        this.formatter = formatter;
    }

    @Override
    public void sendMessage(ViolationDTO violationDTO) throws CommunicationException {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            factory.setRequestedHeartbeat(30);
            factory.setConnectionTimeout(30000);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);

            logger.info("Connection to the violation queue is made");

            String xmlString = (String) formatter.format(violationDTO);

            channel.basicPublish("", queueName, null, xmlString.getBytes());
            logger.info("Sent violation to the IncommingMessageQueue queue: " + queueName);
        } catch (Exception e){
            throw new CommunicationException("Error during placing a violation on the queue", e);
        }
    }

    @Override
    public void shutdown() throws CommunicationException {
        try {
            channel.close();
            connection.close();
            logger.info("Connection to the " + queueName + " closed");
        } catch (TimeoutException | IOException e) {
            throw new CommunicationException("Unable to close connection to IncommingMessageQueue, e");
        }
    }

    public MessageFormatter getFormatter() { return formatter; }
    public void setFormatter(MessageFormatter formatter) { this.formatter = formatter; }
}
