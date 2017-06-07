package adapters.formatters;

import dtos.IncommingMessageDTO;
import domain.CommunicationException;
import domain.messages.LicensePlate;
import domain.services.MessageFormatter;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalTime;


/**
 * Implementation of a {@link MessageFormatter} that uses JDOM to formats an XML string into a {@link IncommingMessageDTO}
 */
public class XMLToDTOFormatter implements MessageFormatter<String, IncommingMessageDTO> {
    private Logger logger = Logger.getLogger(XMLToDTOFormatter.class);

    @Override
    public IncommingMessageDTO format(String messageString) throws CommunicationException {
        IncommingMessageDTO out = new IncommingMessageDTO();

        try {
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(new StringReader(messageString));
            Element rootElement = doc.getRootElement();

            out.setCameraId(Integer.parseInt(rootElement.getChildText("camera-id")));
            out.setTimestamp(LocalTime.parse(rootElement.getChildText("timestamp")));
            out.setLicensePlate(new LicensePlate(rootElement.getChildText("license-plate")));

            logger.info("Tranformed XML string to IncommingMessageDTO: '" + out.toString() + "'");
        } catch (JDOMException | IOException e) {
            throw new CommunicationException("Error during conversion to IncommingMessageDTO", e);
        }

        return out;
    }
}

