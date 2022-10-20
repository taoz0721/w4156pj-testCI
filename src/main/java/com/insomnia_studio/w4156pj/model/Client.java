package com.insomnia_studio.w4156pj.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.UUID;
@Data
public class Client {
    private UUID clientId;
    private String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
    }
}
