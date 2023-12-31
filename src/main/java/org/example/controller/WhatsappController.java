package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.SendMessageBody;
import org.example.dto.WhatsappMessageResponseDto.WhatsappMessageResponseDto;
import org.example.dto.webhooksRequestRecieveDto.WebhookEventBody;
import org.example.service.WhatsappService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/whatsapp/v1")
public class WhatsappController {
    private final WhatsappService whatsAppService;


    @GetMapping("/verification")
    public ResponseEntity<Integer> Verification(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.challenge") Integer challenge,
            @RequestParam("hub.verify_token") String verifyToken) {
        return new ResponseEntity<>(whatsAppService.verification(mode, challenge, verifyToken), HttpStatus.OK);
    }

    @PostMapping("/event-notify")
    public void changeStatus(@RequestBody WebhookEventBody webhookEventBody) {
        whatsAppService.changeMessageStatus(webhookEventBody);
    }

    @PostMapping("/send")
    public ResponseEntity<Mono<WhatsappMessageResponseDto>> sendMessage( @RequestBody @Valid SendMessageBody sendMessageBody) {
        Mono<WhatsappMessageResponseDto> whatsappMessageResponse = whatsAppService.sendMessageService(sendMessageBody);
        return ResponseEntity.ok(whatsappMessageResponse);
    }

}


