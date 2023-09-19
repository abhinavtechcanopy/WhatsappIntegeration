package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.ApplicationConfig;
import org.example.dto.SendMessageBody;
import org.example.dto.WhatsappMessageResponseDto.Contacts;
import org.example.dto.WhatsappMessageResponseDto.ResponseMessage;
import org.example.dto.WhatsappMessageResponseDto.WhatsappMessageResponseDto;
import org.example.dto.webhooksRequestRecieveDto.*;
import org.example.entity.CustomUser;
import org.example.entity.WhatsappMessage;
import org.example.exceptions.InvalidMessageIdException;
import org.example.exceptions.RequestInvalidated;
import org.example.gateway.WhatsappGateway;
import org.example.repository.CustomUserRepository;
import org.example.repository.MessageRepository;
import org.example.util.MessageStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsappService {
    private final CustomUserRepository customUserRepository;
    private final ApplicationConfig applicationConfig;
    private final MessageRepository messageRepository;
    private final WhatsappGateway whatsappGateway;


    public Mono<WhatsappMessageResponseDto> sendMessageService(SendMessageBody sendMessageBody) {
        Mono<WhatsappMessageResponseDto> responseMessage = whatsappGateway.sendMessages(sendMessageBody);
        saveResponse(responseMessage);
        return responseMessage;
    }

    private void saveResponse(Mono<WhatsappMessageResponseDto> responseMessage) {

        responseMessage.subscribe(
                messageBody -> {
                    List<ResponseMessage> responseMessageList = messageBody.getMessages();
                    List<Contacts> contactsList = messageBody.getContacts();
                    WhatsappMessage recievedWhatsappMessage = new WhatsappMessage();
                    recievedWhatsappMessage.setMessageId(responseMessageList.get(0).getId());
                    recievedWhatsappMessage.setMessageStatus(MessageStatus.PENDING);
                    recievedWhatsappMessage.setFromPhoneNumberId(applicationConfig.getFromPhoneNumberId());
                    recievedWhatsappMessage.setToPhoneNumber(contactsList.get(0).getWhatsappId());
                    recievedWhatsappMessage.setDateCreated(new Date());
                    recievedWhatsappMessage.setLastModifiedDate(new Date());
                    /*
                     * Get current userId once you implement spring security
                     */
                    customUserRepository.save(new CustomUser("demoUser1", "testUser"));
                    recievedWhatsappMessage.setCreatedBy(customUserRepository.getById("demoUser1"));

                    messageRepository.save(recievedWhatsappMessage);
                    log.info("saved: {}", recievedWhatsappMessage.getMessageId());

                },
                error -> {
                    log.error("message response not saved");
                    error.printStackTrace();

                });

    }





    public Integer verification(String mode, Integer challenge, String verifyToken) {

        if (mode == null || challenge == null || verifyToken == null)
            throw new IllegalArgumentException("Invalid request: Missing required parameters");

        if ("subscribe".equals(mode) && applicationConfig.getWebhookToken().equals(verifyToken))
            return challenge;

        else throw new RequestInvalidated("request Invalidated");
    }

    public void changeMessageStatus(WebhookEventBody webhookEventBody) {

        for (Entry entry : webhookEventBody.getEntry()) {
            for (Change change : entry.getChanges()) {
                Value value = change.getValue();
                for (Status status : value.getStatuses()) {
                    String messageID = status.getId();
                    MessageStatus messageStatus = status.getStatus();
                    /*
                    User will always be a webhook
                     */
                    Optional<WhatsappMessage> optionalMessageRepository = messageRepository.findById(messageID);
                    if (optionalMessageRepository.isEmpty())
                        throw new InvalidMessageIdException("Invalid message Id");
                    messageRepository.updateMessageStatusAndLastModifiedDateAndUpdatedUserAndLastUpdatedByById(messageID, messageStatus, new Date(), customUserRepository.findById("webhookUser").get());

                }
            }
        }


    }


}
