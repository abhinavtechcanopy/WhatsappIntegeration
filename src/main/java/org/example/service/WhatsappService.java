package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.ApplicationConfig;
import org.example.dto.SendMessageBody;
import org.example.entity.CustomUser;
import org.example.entity.WhatsappMessage;
import org.example.exceptions.IncorrectMessageBody;
import org.example.exceptions.MessageNotSentException;
import org.example.exceptions.RequestInvalidated;
import org.example.repository.MessageRepository;
import org.example.request_models.*;
import org.example.response_models.Contacts;
import org.example.response_models.ResponseMessage;
import org.example.response_models.SendMessageResponse;
import org.example.util.MessageStatus;
import org.example.webhooks_request_models.*;
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
    private final WebClient webClientCustom;
    private final ApplicationConfig applicationConfig;
    private final MessageRepository messageRepository;

    public Mono<SendMessageResponse> sendMessageService(SendMessageBody sendMessageBody) {
        MessageBody messageBody;

        switch (sendMessageBody.getMessageType()) {
            case "template":
                messageBody = getTemplateMessageBody(sendMessageBody);
                break;
            case "text":
                messageBody = getCustomMessageBody(sendMessageBody);
                break;
            default:
                throw new IncorrectMessageBody("Invalid Message Type");
        }
        System.out.println("template generated");

        Mono<SendMessageResponse> responseMessage = performPostRequest(messageBody);

             saveResponse(responseMessage);

        return responseMessage;
    }

    private void saveResponse(Mono<SendMessageResponse> responseMessage) {

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
                    recievedWhatsappMessage.setCreatedBy(new CustomUser("1","testUser1","12345"));
                    System.out.println(recievedWhatsappMessage);
                    messageRepository.save(recievedWhatsappMessage);

                },
                error -> {
                    log.error("message response not saved");
                    error.printStackTrace();

                });

    }

    private Mono<SendMessageResponse> performPostRequest(MessageBody messageBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + applicationConfig.getApiAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("perform post");
        try {
            return webClientCustom.method(HttpMethod.POST)
                    .uri(applicationConfig.getWhatsappBaseUrl() + applicationConfig.getFromPhoneNumberId() + "/messages")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(BodyInserters.fromValue(messageBody))
                    .retrieve()
                    .bodyToMono(SendMessageResponse.class);

        } catch (WebClientResponseException webClientResponseException) {
            throw new MessageNotSentException("Bad request Exception-->" + webClientResponseException.getMessage());
        }
    }

    private MessageBody getCustomMessageBody(SendMessageBody sendMessageBody) {
        Text text = new Text();
        text.setPreviewUrl(false);
        text.setBody(sendMessageBody.getMessage());

        CustomMessageBody customMessageBody = new CustomMessageBody();
        customMessageBody.setMessagingProduct(sendMessageBody.getMessagingProduct());
        customMessageBody.setRecipientType(sendMessageBody.getRecipientType());
        customMessageBody.setTo(sendMessageBody.getToPhoneNumber());
        customMessageBody.setType(sendMessageBody.getMessageType());
        customMessageBody.setText(text);
        return customMessageBody;
    }

    private MessageBody getTemplateMessageBody(SendMessageBody sendMessageBody) {

        Language language = new Language();
        language.setCode(sendMessageBody.getLanguageCode());

        Template template = new Template();
        template.setName(sendMessageBody.getMessage());
        template.setLanguage(language);

        TemplateMessageBody templateMessageBody = new TemplateMessageBody();
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
                    messageRepository.updateMessageStatusById(messageID, messageStatus, new Date());
                }
            }
        }


    }
}
