package adapters.formatters;

import dtos.CameraServiceMessageDTO;
import com.sun.istack.internal.logging.Logger;
import domain.CommunicationException;
import domain.messages.Location;
import domain.messages.Segment;
import domain.services.MessageFormatter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implementation of a {@link MessageFormatter} that formats a JSON string into a {@link CameraServiceMessageDTO}
 */
public class JSONCameraMessageFormatter implements MessageFormatter<String, CameraServiceMessageDTO> {
    private Logger logger = Logger.getLogger(JSONCameraMessageFormatter.class);

    @Override
    public CameraServiceMessageDTO format(String messageString) throws CommunicationException {
        CameraServiceMessageDTO messageDTO = new CameraServiceMessageDTO();

        try {
            JSONObject body = new JSONObject(messageString);

            logger.info("Received response: " + body);

            if(body.has("error"))
                throw new CommunicationException("Error response received");

            messageDTO.setCameraId(body.getInt("cameraId"));

            JSONObject locationObject = body.getJSONObject("location");
            Location location = new Location();
            location.setLat(locationObject.getInt("lat"));
            location.setLongitude(locationObject.getInt("long"));
            messageDTO.setLocation(location);

            if (body.has("segment")) {
                logger.info("Body has segment");
                JSONObject sentimentObject = body.getJSONObject("segment");
                Segment segment = new Segment();
                segment.setConnectedCameraId(sentimentObject.getInt("connectedCameraId"));
                segment.setDistance(sentimentObject.getInt("distance"));
                segment.setSpeedlimit(sentimentObject.getInt("speedLimit"));
                messageDTO.setSegment(segment);
            }

            if (body.has("euroNorm")) {
                logger.info("Body has euroNorm");
                messageDTO.setEuroNormAllowed(body.getInt("euroNorm"));
            }

            logger.info("Proxy answer transformed to CameraServiceMessageDTO: " + messageDTO.toString());
        } catch (JSONException e) {
            throw new CommunicationException("Unable to transform JSON to CameraServiceMessageDTO", e);
        }

        return messageDTO;
    }
}
