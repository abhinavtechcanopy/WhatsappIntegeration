package org.example.gateway;

import lombok.RequiredArgsConstructor;
import org.example.configuration.ApplicationConfig;
import org.example.dto.SendMessageBody;
import org.example.dto.WhatsappMessageResponseDto.WhatsappMessageResponseDto;
import org.example.dto.whatsappMessageRequestDto.*;
import org.example.exceptions.IncorrectMessageBody;
import org.example.exceptions.MessageNotSentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WhatsappGateway {
    private final WebClient webClientCustom;
    private final ApplicationConfig applicationConfig;

    public Mono<WhatsappMessageResponseDto> sendMessages(SendMessageBody whatsappSendMessageBodyType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + applicationConfig.getApiAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Object body = switch (whatsappSendMessageBodyType.getMessageType()) {
            case "template" -> getTemplateMessageBody(whatsappSendMessageBodyType);
            case "text" -> getTextMessageBody(whatsappSendMessageBodyType);
            default -> throw new IncorrectMessageBody("Invalid messageType");
        };

        try {
            return webClientCustom.method(HttpMethod.POST)
                    .uri(applicationConfig.getWhatsappBaseUrl() + applicationConfig.getFromPhoneNumberId() + "/messages")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(BodyInserters.fromValue(body))
                    .retrieve()
                    .bodyToMono(WhatsappMessageResponseDto.class);

        } catch (WebClientResponseException webClientResponseException) {
            throw new MessageNotSentException("Bad request Exception-->" + webClientResponseException.getMessage());
        }
    }

    private WhatsappTextMessage getTextMessageBody(SendMessageBody sendMessageBody) {
        Text text = new Text();
        text.setPreviewUrl(false);
        text.setBody(sendMessageBody.getMessage());

        WhatsappTextMessage customMessageBody = new WhatsappTextMessage();
        customMessageBody.setMessagingProduct(sendMessageBody.getMessagingProduct());
        customMessageBody.setRecipientType(sendMessageBody.getRecipientType());
        customMessageBody.setTo(sendMessageBody.getToPhoneNumber());
        customMessageBody.setType(sendMessageBody.getMessageType());
        customMessageBody.setText(text);
        return customMessageBody;
    }

    private WhatsappTemplateMessage getTemplateMessageBody(SendMessageBody sendMessageBody) {

        Language language = new Language();
        language.setCode(sendMessageBody.getLanguageCode());

        Template template = new Template();
        template.setName(sendMessageBody.getMessage());
        template.setLanguage(language);

        WhatsappTemplateMessage templateMessageBody = new WhatsappTemplateMessage();
        templateMessageBody.setMessagingProduct(sendMessageBody.getMessagingProduct());
        templateMessageBody.setTo(sendMessageBody.getToPhoneNumber());
        templateMessageBody.setType(sendMessageBody.getMessageType());
        templateMessageBody.setTemplate(template);

        return templateMessageBody;
    }
}
