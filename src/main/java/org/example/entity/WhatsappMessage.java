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
@Table(name = "whatsapp_message")
public class WhatsappMessage extends AuditableEntity {
    @Id
    @Column(name = "message_id")
    private String messageId;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_status")
    private MessageStatus messageStatus;
    @Column(name = "to_phone_number")
    private String toPhoneNumber;
    @Column(name = "from_phone_number_id")
    private String fromPhoneNumberId;
}
