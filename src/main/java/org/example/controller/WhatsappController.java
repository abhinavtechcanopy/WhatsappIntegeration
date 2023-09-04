package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.SendMessageBody;
import org.example.repository.MessageRepository;
import org.example.response_models.SendMessageResponse;
import org.example.service.WhatsappService;
import org.example.webhooks_request_models.WebhookEventBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
public class WhatsappController {
    private final WhatsappService whatsAppService;
    private final MessageRepository messageRepository;

    @GetMapping("/verification")
    public ResponseEntity<Integer> Verification(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") Integer challenge,
            @RequestParam("hub.verify_token") String verifyToken) {
        return new ResponseEntity<Integer>(whatsAppService.verification(mode,challenge,verifyToken), HttpStatus.OK);

    }

    @PostMapping("event-notify")
    public void changeStatus(@RequestBody WebhookEventBody webhookEventBody) {
        System.out.println(webhookEventBody);
        whatsAppService.changeMessageStatus(webhookEventBody);

    }

    @PostMapping("/send")
    public ResponseEntity<Mono<SendMessageResponse>> sendMessage(@RequestBody @Valid SendMessageBody sendMessageBody) {
        System.out.println("inside send message");
        Mono<SendMessageResponse> whatsappMessageResponse = whatsAppService.sendMessageService(sendMessageBody);
        return ResponseEntity.ok(whatsappMessageResponse);
    }


}