package org.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Integer error;
    private String message;
}
