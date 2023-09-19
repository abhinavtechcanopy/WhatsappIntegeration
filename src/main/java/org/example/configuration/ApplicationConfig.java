package org.example.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
@Setter
public class ApplicationConfig {

    @Value("${whatsapp.phone-number.id}")
    private String fromPhoneNumberId;
    @Value("${whatsapp.base-url}")
    private String whatsappBaseUrl;
    @Value("${whatsapp.access-token}")
    private String apiAccessToken;
    @Value("${whatsapp.web-hook.verify-token}")
    private String webhookToken;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().codecs(clientCodecConfigurer ->
                        clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder())
                )
                .build();
    }
}
