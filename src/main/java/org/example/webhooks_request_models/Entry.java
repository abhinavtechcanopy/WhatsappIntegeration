package org.example.webhooks_request_models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
public class Entry {
    private String id;
    private List<Change> changes;
}
