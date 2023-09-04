package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.util.MessageStatus;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="whatsapp_message")
public class WhatsappMessage extends AuditableEntity {
    @Id
    private String messageId;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;
    private String toPhoneNumber;
    private String fromPhoneNumberId;



}
