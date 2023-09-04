package org.exampleTest.serviceTest;

import org.example.dto.SendMessageBody;
import org.junit.jupiter.api.Test;

public class WhatsappServiceTest {

    @Test
    void testSendMessageServiceWithTemplateMessageType() {
        // Arrange
        SendMessageBody request = new SendMessageBody(); // Replace with valid request data
        request.setMessagingProduct("SomeProduct");
        request.setRecipientType("SomeRecipient");
        request.setToPhoneNumber("123456789012");
        request.setMessageType("SomeType");
        request.setMessage("Hello, World!");
        request.setLanguageCode("en");
//        WhatsappService service = new WhatsappService();








//
//
//        // Mock the necessary methods
//        when(service.getTemplateMessageBody(request)).thenReturn(new MessageBody()); // Replace with a valid message body
//        when(service.performPostRequest(any())).thenReturn(Mono.just(new WhatsappMessageResponse())); // Mocking the WebClient call
//
//        // Act
//        Mono<WhatsappMessageResponse> response = service.sendMessageService(request);
//
//        // Assert
//        // You can use reactor-test StepVerifier or other methods to assert the response
//        // For example, you can assert that the response has the expected values or is not empty.
    }


}
