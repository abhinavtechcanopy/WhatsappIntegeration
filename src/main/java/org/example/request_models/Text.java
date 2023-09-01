package org.example.request_models;

import lombok.Data;
import lombok.Setter;


@Data
@Setter
public class Text {
    private Boolean preview_url;
    private String body;

}
