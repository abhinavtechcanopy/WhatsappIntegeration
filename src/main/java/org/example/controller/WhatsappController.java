package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.WhatsappMessageRequest;
import org.example.webhooks_models.WebhookResponseStatus;
import org.example.response_models.WhatsappMessageResponse;
import org.example.service.WhatsappService;
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

    @GetMapping("/verification/webhooks")
    public ResponseEntity<Integer> Verification(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") Integer challenge,
            @RequestParam("hub.verify_token") String verifyToken) {

        return new ResponseEntity<Integer>(whatsAppService.verification(mode,challenge,verifyToken), HttpStatus.OK);

    }

    @PostMapping("event-notify/webhooks")
    public void changeStatus(@RequestBody WebhookResponseStatus webhookResponseStatus){

    }

    @PostMapping("/send")
    public ResponseEntity<Mono<WhatsappMessageResponse>> sendMessage(@RequestBody @Valid WhatsappMessageRequest whatsappMessageRequest) {
        Mono<WhatsappMessageResponse> whatsappMessageResponse = whatsAppService.sendMessageService(whatsappMessageRequest);
        return ResponseEntity.ok(whatsappMessageResponse);
    }


}