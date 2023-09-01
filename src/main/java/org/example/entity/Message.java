package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.util.MessageStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;
    private String toPhoneNumber;
    private String fromPhoneNumberId;


}
