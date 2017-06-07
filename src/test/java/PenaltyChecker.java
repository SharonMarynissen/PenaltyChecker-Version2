import adapters.externalServiceAdapters.CameraService;
import adapters.externalServiceAdapters.LicensePlateService;
import adapters.formatters.DTOToStringFormatter;
import adapters.formatters.JSONCameraMessageFormatter;
import adapters.formatters.JSONLicensePlateMessageFormatter;
import adapters.formatters.XMLToDTOFormatter;
import adapters.queues.IncommingMessageQueue;
import adapters.queues.ViolationQueue;
import domain.Manager;
import domain.services.*;

/**
 * Created by Sharon on 6/06/2017.
 */
public class PenaltyChecker {
    public static void main(String[] args) {
        final String uriInput = "amqp://garalhym:6TYjhiUm_wXY47k0MZ8YQGuLYAtS-65U@puma.rmq.cloudamqp.com/garalhym";
        final String uriOutput = "amqp://ssgtyban:xuDLGGEDGZ5Toci-gDcUMnbG7IG2M294@puma.rmq.cloudamqp.com/ssgtyban";

        InputService inputService = new IncommingMessageQueue(uriInput, "CAMERA MESSAGES", new XMLToDTOFormatter());
        OutputService outputService = new ViolationQueue(uriOutput, "VIOLATIONS", new DTOToStringFormatter());
        CameraInformationService cameraInformationService = new CameraService(new JSONCameraMessageFormatter());
        LicensePlateInformationService licensePlateInformationService = new LicensePlateService(new JSONLicensePlateMessageFormatter());

        Manager manager = new Manager(inputService);
        manager.setOutputService(outputService);
        manager.setCameraInformationService(cameraInformationService);
        manager.setLicensePlateInformationService(licensePlateInformationService);

        //manager.setTimeToKeepMessagesInBuffer(10);

        manager.start();
    }
}
