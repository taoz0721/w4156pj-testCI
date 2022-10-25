package com.insomnia_studio.w4156pj.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private UUID clientId;
    private String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
    }
}
