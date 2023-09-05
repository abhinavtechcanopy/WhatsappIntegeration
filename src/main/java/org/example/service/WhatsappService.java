package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.ApplicationConfig;
import org.example.dto.SendMessageBody;
import org.example.dto.WhatsappMessageResponseDto.Contacts;
import org.example.dto.WhatsappMessageResponseDto.ResponseMessage;
import org.example.dto.WhatsappMessageResponseDto.WhatsappMessageResponseDto;
import org.example.dto.webhooksRequestRecieveDto.*;
import org.example.dto.whatsappMessageRequestDto.*;
import org.example.entity.WhatsappMessage;
import org.example.exceptions.IncorrectMessageBody;
import org.example.exceptions.MessageNotSentException;
import org.example.exceptions.RequestInvalidated;
import org.example.repository.CustomUserRepository;
import org.example.repository.MessageRepository;
import org.example.util.MessageStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsappService {
    private final CustomUserRepository customUserRepository;
    private final WebClient webClientCustom;
    private final ApplicationConfig applicationConfig;
    private final MessageRepository messageRepository;

    public Mono<WhatsappMessageResponseDto> sendMessageService(SendMessageBody sendMessageBody) {
        whatsappSendMessageBodyType whatsappSendMessageBodyType;

        switch (sendMessageBody.getMessageType()) {
            case "template":
                whatsappSendMessageBodyType = getTemplateMessageBody(sendMessageBody);
                break;
            case "text":
                whatsappSendMessageBodyType = getCustomMessageBody(sendMessageBody);
                break;
            default:
                throw new IncorrectMessageBody("Invalid Message Type");
        }
        System.out.println("template generated");

        Mono<WhatsappMessageResponseDto> responseMessage = performPostRequest(whatsappSendMessageBodyType);

             saveResponse(responseMessage);

        return responseMessage;
    }

    private void saveResponse(Mono<WhatsappMessageResponseDto> responseMessage) {

        responseMessage.subscribe(
                messageBody -> {

                    String message = messageBody.getMessagingProduct();
                    List<ResponseMessage> responseMessageList = messageBody.getMessages();
                    List<Contacts> contactsList = messageBody.getContacts();
                    WhatsappMessage recievedWhatsappMessage = new WhatsappMessage();
                    recievedWhatsappMessage.setMessageId(responseMessageList.get(0).getId());
                    recievedWhatsappMessage.setMessageStatus(MessageStatus.PENDING);
                    recievedWhatsappMessage.setFromPhoneNumberId(applicationConfig.getFromPhoneNumberId());
                    recievedWhatsappMessage.setToPhoneNumber(contactsList.get(0).getWhatsappId());
                    recievedWhatsappMessage.setDateCreated(new Date());
                    /*
                     * Get current userId once you implement spring security
                     */
                    recievedWhatsappMessage.setCreatedByUser(customUserRepository.getById("demoUser1"));
                    System.out.println(recievedWhatsappMessage);
                    messageRepository.save(recievedWhatsappMessage);

                },
                error -> {
                    log.error("message response not saved");
                    error.printStackTrace();

                });

    }

    private Mono<WhatsappMessageResponseDto> performPostRequest(whatsappSendMessageBodyType whatsappSendMessageBodyType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + applicationConfig.getApiAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("perform post");
        try {
            return webClientCustom.method(HttpMethod.POST)
                    .uri(applicationConfig.getWhatsappBaseUrl() + applicationConfig.getFromPhoneNumberId() + "/messages")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(BodyInserters.fromValue(whatsappSendMessageBodyType))
                    .retrieve()
                    .bodyToMono(WhatsappMessageResponseDto.class);

        } catch (WebClientResponseException webClientResponseException) {
            throw new MessageNotSentException("Bad request Exception-->" + webClientResponseException.getMessage());
        }
    }

    private whatsappSendMessageBodyType getCustomMessageBody(SendMessageBody sendMessageBody) {
        Text text = new Text();
        text.setPreviewUrl(false);
        text.setBody(sendMessageBody.getMessage());

        WhatsappTextMessageBody customMessageBody = new WhatsappTextMessageBody();
        customMessageBody.setMessagingProduct(sendMessageBody.getMessagingProduct());
        customMessageBody.setRecipientType(sendMessageBody.getRecipientType());
        customMessageBody.setTo(sendMessageBody.getToPhoneNumber());
        customMessageBody.setType(sendMessageBody.getMessageType());
        customMessageBody.setText(text);
        return customMessageBody;
    }

    private whatsappSendMessageBodyType getTemplateMessageBody(SendMessageBody sendMessageBody) {

        Language language = new Language();
        language.setCode(sendMessageBody.getLanguageCode());

        Template template = new Template();
        template.setName(sendMessageBody.getMessage());
        template.setLanguage(language);

        WhatsappTemplateMessageBodyType templateMessageBody = new WhatsappTemplateMessageBodyType();
        templateMessageBody.setMessagingProduct(sendMessageBody.getMessagingProduct());
        templateMessageBody.setTo(sendMessageBody.getToPhoneNumber());
        templateMessageBody.setType(sendMessageBody.getMessageType());
        templateMessageBody.setTemplate(template);

        return templateMessageBody;
    }

    public Integer verification(String mode, Integer challenge, String verifyToken) {
        System.out.println("inside service verification");
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

                    messageRepository.updateMessageStatusAndDateUpdatedAndUpdatedUserAndLastUpdatedByUserById(messageID, messageStatus, new Date(), customUserRepository.findById("webhookUser").get());

                }
            }
        }


    }
}
