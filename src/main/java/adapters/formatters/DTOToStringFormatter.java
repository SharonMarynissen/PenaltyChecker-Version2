package adapters.formatters;

import domain.CommunicationException;
import domain.services.MessageFormatter;
import dtos.ViolationDTO;
import org.apache.log4j.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 * A formatter that uses JDOM to transfer a {@link ViolationDTO} into an XML string
 */

public class DTOToStringFormatter implements MessageFormatter<ViolationDTO, String> {
    private Logger logger = Logger.getLogger(XMLToDTOFormatter.class);

    @Override
    public String format(ViolationDTO messageDTO) throws CommunicationException {
        Element rootElement = new Element("Violation");
        Document doc = new Document(rootElement);

        Element typeElement = new Element("type");
        typeElement.setText(String.valueOf(messageDTO.getType()));
        rootElement.addContent(typeElement);

        Element timeElement = new Element("timestamp");
        timeElement.setText(String.valueOf(messageDTO.getTimestamp()));
        rootElement.addContent(timeElement);

        Element licensePlateElement = new Element("license-plate");
        licensePlateElement.setText(messageDTO.getLicensePlate().getPlate());
        rootElement.addContent(licensePlateElement);

        Element locationElement = new Element("location");
        Element latitudeElement = new Element("lat");
        latitudeElement.setText(String.valueOf(messageDTO.getLocation().getLat()));
        locationElement.addContent(latitudeElement);
        Element longtitudeElement = new Element("long");
        longtitudeElement.setText(String.valueOf(messageDTO.getLocation().getLongitude()));
        locationElement.addContent(longtitudeElement);
        rootElement.addContent(locationElement);

        Element nationalNumberElement = new Element("national-number");
        nationalNumberElement.setText(messageDTO.getNationalNumber());
        rootElement.addContent(nationalNumberElement);

        if (messageDTO.getSpeed() != -1){
            Element speedElement = new Element("speed");
            speedElement.setText(String.valueOf(messageDTO.getSpeed()));
            rootElement.addContent(speedElement);

            Element maxSpeedElement = new Element("maximum-speed");
            maxSpeedElement.setText(String.valueOf(messageDTO.getMaximumSpeed()));
            rootElement.addContent(maxSpeedElement);
        }

        if (messageDTO.getEuroNormVehicle() != -1) {
            Element euroNormAllowedElement = new Element("euronorm-allowed");
            euroNormAllowedElement.setText(String.valueOf(messageDTO.getEuroNormAllowed()));
            rootElement.addContent(euroNormAllowedElement);

            Element euroNormVehicle = new Element("euronorm-vehicle");
            euroNormVehicle.setText(String.valueOf(messageDTO.getEuroNormVehicle()));
            rootElement.addContent(euroNormVehicle);
        }

        XMLOutputter xmlOutputter = new XMLOutputter();

        String out = xmlOutputter.outputString(doc);
        logger.info("Changed MessageDTO to XML: '" + out + "'");

        return out;
    }
}
