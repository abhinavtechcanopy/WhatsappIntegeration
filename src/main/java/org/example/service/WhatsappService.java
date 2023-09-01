package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.ApplicationConfig;
import org.example.dto.WhatsappMessageRequest;
import org.example.exceptions.IncorrectMessageBody;
import org.example.exceptions.MessageNotSentException;
import org.example.exceptions.RequestValidationfailed;
import org.example.request_models.*;
import org.example.response_models.WhatsappMessageResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsappService {
    private final WebClient webClientCustom;
    private final ApplicationConfig applicationConfig;

    public Mono< WhatsappMessageResponse> sendMessageService(WhatsappMessageRequest whatsappMessageRequest) {
        MessageBody messageBody;

        switch (whatsappMessageRequest.getMessageType()) {
            case "template":
                messageBody = getTemplateMessageBody(whatsappMessageRequest);
                break;
            case "text":
                messageBody = getCustomMessageBody(whatsappMessageRequest);
                break;
            default:
                throw new IncorrectMessageBody("Invalid Message Type");
        }

      Mono< WhatsappMessageResponse > responseMessage= performPostRequest(messageBody);

        return responseMessage;
    }

    private Mono< WhatsappMessageResponse> performPostRequest(MessageBody messageBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + applicationConfig.getApiAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
      return webClientCustom.method(HttpMethod.POST)
                    .uri(applicationConfig.getWhatsappBaseUrl() + applicationConfig.getFromPhoneNumberId() + "/messages")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(BodyInserters.fromValue(messageBody))
                    .retrieve()
                    .bodyToMono(WhatsappMessageResponse.class);
        } catch (WebClientResponseException webClientResponseException) {
            throw new MessageNotSentException("Bad request Exception" + webClientResponseException.getMessage());
        }
    }

    private MessageBody getCustomMessageBody(WhatsappMessageRequest whatsappMessageRequest) {
        Text text = new Text();
        text.setPreview_url(false);
        text.setBody(whatsappMessageRequest.getMessage());

        CustomMessageBody customMessageBody = new CustomMessageBody();
        customMessageBody.setMessaging_product(whatsappMessageRequest.getMessagingProduct());
        customMessageBody.setRecipient_type(whatsappMessageRequest.getRecipientType());
        customMessageBody.setTo(whatsappMessageRequest.getToPhoneNumber());
        customMessageBody.setType(whatsappMessageRequest.getMessageType());
        customMessageBody.setText(text);
        return customMessageBody;
    }

    private MessageBody getTemplateMessageBody(WhatsappMessageRequest whatsappMessageRequest) {

        Language language = new Language();
        language.setCode(whatsappMessageRequest.getLanguageCode());

        Template template = new Template();
        template.setName(whatsappMessageRequest.getMessage());
        template.setLanguage(language);

        TemplateMessageBody templateMessageBody = new TemplateMessageBody();
        templateMessageBody.setMessaging_product(whatsappMessageRequest.getMessagingProduct());
        templateMessageBody.setTo(whatsappMessageRequest.getToPhoneNumber());
        templateMessageBody.setType(whatsappMessageRequest.getMessageType());
        templateMessageBody.setTemplate(template);

        return templateMessageBody;
    }

    public Integer verification(String mode, Integer challenge, String verifyToken) {
        if("subscribe".equals(mode) && applicationConfig.getWebhookToken().equals(verifyToken))
            return challenge;
        throw new RequestValidationfailed("request Invalidated");
    }
}
