package domain;

import domain.checkers.EuroNormChecker;
import domain.checkers.SpeedLimitChecker;
import domain.messages.Message;
import domain.services.*;
import domain.violations.EuroNormViolation;
import domain.violations.SpeedViolation;
import dtos.*;
import org.apache.log4j.Logger;

/**
 * Mediator of coupling all the different services and calling them at the right time
 */
public class Manager implements InputListener {
    private InputService inputService;
    private Logger logger = Logger.getLogger(Manager.class);
    CameraInformationService cameraInformationService;
    LicensePlateInformationService licensePlateInformationService;
    private OutputService outputService;
    private MessageBuffer buffer;
    private SpeedLimitChecker speedLimitChecker;

    public Manager(InputService inputService) {
        this.inputService = inputService;
        this.buffer = new MessageBuffer();
        this.speedLimitChecker = new SpeedLimitChecker();
    }

    public void start() {
        try {
            inputService.initialize(this);
        } catch (CommunicationException e) {
            logger.fatal("Unable to initialize communication channel", e);
        }
    }

    public void stop() {
        try {
            inputService.shutdown();
            if(outputService != null)
                outputService.shutdown();
        } catch (CommunicationException e) {
            logger.warn("Unable to properly shut down communication channel");
        }
    }

    @Override
    public void onReceive(IncomingMessageDTO messageDTO) {
        logger.info("Received message from InputService");
        Message message = new Message(messageDTO);

        if (cameraInformationService != null) {
            try {
                LicensePlateServiceMessageDTO licensePlateServiceMessageDTO = null;
                SpeedViolation speedViolation = null;
                EuroNormViolation euroNormViolation = null;
                ViolationDTO violation = null;


                CameraServiceMessageDTO cameraServiceMessageDTO = cameraInformationService.getInfo(messageDTO);
                message.addInfo(cameraServiceMessageDTO);
                Message messageFromBuffer = buffer.checkMessage(message);

                if(licensePlateInformationService != null){
                    if (message.getEuroNormAllowed() != -1){
                        licensePlateServiceMessageDTO = licensePlateInformationService.getInfo(messageDTO);
                        message.addInfo(licensePlateServiceMessageDTO);
                        euroNormViolation = new EuroNormChecker().checkMessage(message);
                    }

                    if(euroNormViolation != null){
                        violation = new ViolationDTO(euroNormViolation);
                        sendToOutputService(violation);
                    }

                    if (messageFromBuffer != null){
                        speedViolation = speedLimitChecker.checkForViolation(messageFromBuffer, message);
                    }

                    if (speedViolation != null){
                        if (message.getNationalNumber() == null) {
                            licensePlateServiceMessageDTO = licensePlateInformationService.getInfo(messageDTO);
                            speedViolation.setNationalNumber(licensePlateServiceMessageDTO.getNationalNumber());
                            logger.info("Added national number to the speed violation... Speedviolation is ready to send out");
                        }
                        violation = new ViolationDTO(speedViolation);
                        sendToOutputService(violation);
                    }
                }
            } catch (CommunicationException e) {
                logger.error("Unexpected error during message handling", e);
            }
        }
    }

    public void setOutputService(OutputService outputService) {
        this.outputService = outputService;
    }

    public void setCameraInformationService(CameraInformationService cameraInformationService) {
        this.cameraInformationService = cameraInformationService;
    }

    public void setLicensePlateInformationService(LicensePlateInformationService licensePlateInformationService) {
        this.licensePlateInformationService = licensePlateInformationService;
    }

    public void setTimeToKeepMessagesInBuffer(int time){
        buffer.setTimer(time);
    }

    private void sendToOutputService(ViolationDTO violationDTO){
        if (outputService != null)
            try {
                outputService.sendMessage(violationDTO);
                outputService.shutdown();
            } catch (CommunicationException e) {
                logger.error("Error during placing violation on outgoing queue");
            }
    }
}
