package adapters.formatters;

import dtos.LicensePlateServiceMessageDTO;
import domain.CommunicationException;
import domain.services.MessageFormatter;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation of a {@link MessageFormatter} that formats a JSON string into a {@link LicensePlateServiceMessageDTO}
 */
public class JSONLicensePlateMessageFormatter implements MessageFormatter<String, LicensePlateServiceMessageDTO> {
    private Logger logger = Logger.getLogger(JSONLicensePlateMessageFormatter.class);

    @Override
    public LicensePlateServiceMessageDTO format(String messageString) throws CommunicationException {
        LicensePlateServiceMessageDTO messageDTO = new LicensePlateServiceMessageDTO();

        try {
            JSONObject body = new JSONObject(messageString);

            logger.info("Received response: " + body);

            if (body.has("error"))
                throw new CommunicationException("Error response received");

            messageDTO.setPlateId(body.getString("plateId"));
            messageDTO.setNationalNumber(body.getString("nationalNumber"));
            messageDTO.setEuroNumber(body.getInt("euroNumber"));

            logger.info("Proxy answer transformed to LicensePlateServiceMessageDTO: " + messageDTO);

        } catch (JSONException e) {
            throw new CommunicationException("Unable to transform JSON to LicensePlateServiceMessageDTO", e);
        }

        return messageDTO;
    }
}
