package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Getter
@Setter
public class CustomUser {

    @Id
    private String userId;
    @Size(min = 2,message = "username should at least be 2 characters")
    private String username;

    private String password;

}
